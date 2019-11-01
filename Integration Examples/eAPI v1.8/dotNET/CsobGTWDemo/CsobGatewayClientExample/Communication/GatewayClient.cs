using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication.DataObjects;
using CsobGatewayClientExample.Security;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication
{
    public class GatewayClient
    {
        static Lazy<HttpClient> httpClient = new Lazy<HttpClient>(() => CreateHttpClient(autoRedirectAllow: true));
        static Lazy<HttpClient> httpClientAutoRedirectDisabled = new Lazy<HttpClient>(() => CreateHttpClient(autoRedirectAllow: false));

        private static HttpClient CreateHttpClient(bool autoRedirectAllow)
        {
            HttpClientHandler httpClientHandler = new HttpClientHandler();
            httpClientHandler.AllowAutoRedirect = autoRedirectAllow;

            var client = new HttpClient(httpClientHandler);
            return client;
        }

        public GatewayClient(string merchantId, string privateKeyFilePath)
        {
            if (string.IsNullOrWhiteSpace(merchantId))
            {
                throw new ArgumentException("message", nameof(merchantId));
            }

            if (string.IsNullOrWhiteSpace(privateKeyFilePath))
            {
                throw new ArgumentException("message", nameof(privateKeyFilePath));
            }

            MerchantId = merchantId;
            pemKey = Crypto.DecodePemKey(File.ReadAllText(privateKeyFilePath));
        }

        public string MerchantId { get; }

        private byte[] pemKey;

        private void FillAndSign(SignBaseRequest request)
        {
            request.FillDateTime();
            request.MerchantId = MerchantId;
            request.Signature = Crypto.Sign(request.ToSign(), pemKey);
        }

        public async Task<ClientResponse> CallEchoGetAsync() => await CallEchoAsync(RequestType.Get);

        public async Task<ClientResponse> CallEchoPostAsync() => await CallEchoAsync(RequestType.Post);

        public async Task<ClientResponse> CallProcessAsync(string payId) => await CallPaymenProcessAsync(payId, "payment/process");

        public async Task<ClientResponse> CallStatusAsync(string payId) => await CallPaymenProcessAsync(payId, "payment/status");

        public async Task<ClientResponse> CallInitAsync()
        {
            PayInitReq request;
            using (var file = File.OpenText(Constants.PaymentInitBaseFilePath))
                request = JsonConvert.DeserializeObject<PayInitReq>(file.ReadToEnd());

            FillAndSign(request);

            return await CreatePostRequestAsync("payment/init", request);
        }

        public async Task<ClientResponse> CallReverseAsync(string payId)
        {
            var request = new PayReq()
            {
                PayId = payId
            };

            FillAndSign(request);

            return await CreatePutRequestAsync("payment/reverse", request);
        }

        public async Task<ClientResponse> CallRefundAsync(string payId, long amount)
        {
            var request = new PayRefundReq()
            {
                PayId = payId,
                Amount = amount
            };

            FillAndSign(request);

            return await CreatePutRequestAsync("payment/refund", request);
        }

        private async Task<ClientResponse> CallPaymenProcessAsync(string payId, string method)
        {
            var request = new PayReq()
            {
                PayId = payId
            };

            FillAndSign(request);

            return await CreateGetRequestAsync(method, request, true, (method != "payment/process"));
        }

        public async Task<ClientResponse> CallCloseAsync(string payId)
        {
            var request = new PayReq()
            {
                PayId = payId
            };

            FillAndSign(request);

            return await CreatePutRequestAsync("payment/close", request);
        }

        private async Task<ClientResponse> CallEchoAsync(RequestType type)
        {
            var request = new EchoRequest();
            FillAndSign(request);

            if (type == RequestType.Get)
                return await CreateGetRequestAsync("echo", request);
            else
                return await CreatePostRequestAsync("echo", request);
        }

        public async Task<ClientResponse> CallOneClickInitAsync(string payId)
        {
            PayOneclickInitReq request;
            using (var file = File.OpenText(Constants.PaymentOneclickBaseFilePath))
                request = JsonConvert.DeserializeObject<PayOneclickInitReq>(file.ReadToEnd());

            FillAndSign(request);

            return await CreatePostRequestAsync("payment/oneclick/init", request);
        }

        public async Task<ClientResponse> CallOneClickStartAsync(string payId)
        {
            var request = new PayReq()
            {
                PayId = payId
            };

            FillAndSign(request);

            return await CreatePostRequestAsync("payment/oneclick/start", request);
        }

        public async Task<ClientResponse> CallCustomerInfoAsync(string customerId)
        {
            var request = new CustReq()
            {
                CustomerId = customerId
            };

            FillAndSign(request);

            return await CreateGetRequestAsync("customer/info", request);
        }

        private async Task<ClientResponse> CreatePostRequestAsync(string method, IBaseRequest request)
        {
            var url = $"{Constants.GatewayUrl}/{method}";
            var content = new StringContent(JsonConvert.SerializeObject(request), Encoding.UTF8, "application/json");

            return await PostRequestResponseAsync(url, content);
        }

        private async Task<ClientResponse> CreatePutRequestAsync(string method, IBaseRequest request)
        {
            var url = $"{Constants.GatewayUrl}/{method}";
            var content = new StringContent(JsonConvert.SerializeObject(request), Encoding.UTF8, "application/json");

            return await PutRequestResponseAsync(url, content);
        }

        private async Task<ClientResponse> CreateGetRequestAsync(string method, EchoRequest request)
        {
            var url =
                $"{Constants.GatewayUrl}/" +
                $"{method}/" +
                $"{HttpUtility.UrlEncode(request.MerchantId)}/" +
                $"{request.DateTime}/" +
                $"{HttpUtility.UrlEncode(request.Signature, Encoding.UTF8)}";

            return await GetRequestResponseAsync(url);
        }

        private async Task<ClientResponse> CreateGetRequestAsync(string method, PayReq request, bool actLikeBrowser = false, bool autoRedirectHeader = true)
        {
            var url =
                $"{Constants.GatewayUrl}/" +
                $"{method}/" +
                $"{HttpUtility.UrlEncode(request.MerchantId)}/" +
                $"{request.PayId}/" +
                $"{request.DateTime}/" +
                $"{HttpUtility.UrlEncode(request.Signature, Encoding.UTF8)}";

            return await GetRequestResponseAsync(url, actLikeBrowser, autoRedirectHeader);
        }

        private async Task<ClientResponse> CreateGetRequestAsync(string method, CustReq request)
        {
            var url =
                $"{Constants.GatewayUrl}/" +
                $"{method}/" +
                $"{HttpUtility.UrlEncode(request.MerchantId)}/" +
                $"{request.CustomerId}/" +
                $"{request.DateTime}/" +
                $"{HttpUtility.UrlEncode(request.Signature, Encoding.UTF8)}";

            return await GetRequestResponseAsync(url);
        }

        private async Task<ClientResponse> GetRequestResponseAsync(string url, bool actLikeBrowser = false, bool autoRedirectHeader = true)
        {
            try
            {
                HttpClient client = (autoRedirectHeader ? httpClient : httpClientAutoRedirectDisabled).Value;

                using (var request = new HttpRequestMessage(HttpMethod.Get, url))
                {
                    if (actLikeBrowser)
                    {
                        request.Headers.TryAddWithoutValidation("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.2; .NET CLR 1.0.3705;)");
                    }

                    using (var response = await client.SendAsync(request))
                    {
                        if (response.StatusCode == HttpStatusCode.RedirectMethod)
                        {
                            return new ClientResponse()
                            {
                                ResponseValue = response.Headers.Location.ToString(),
                                ResponseCode = "303 - RedirectMethod"

                            };
                        }
                        else if (response.StatusCode == HttpStatusCode.OK)
                        {
                            using (var content = response.Content)
                            {
                                var stringContent = await content.ReadAsStringAsync();

                                return new ClientResponse()
                                {
                                    ResponseCode = "200 - OK",
                                    ResponseValue =
                                        content.Headers.ContentType.MediaType == "text/html"
                                            ? "Content is html page."
                                            : stringContent
                                };
                            }
                        }
                        else
                        {
                            return new ClientResponse()
                            {
                                ResponseCode = response.StatusCode.ToString(),
                                ResponseValue = response.ReasonPhrase
                            };
                        }
                    }
                }
            }
            catch (Exception e)
            {
                return new ClientResponse()
                {
                    ResponseCode = string.Empty,
                    ResponseValue = e.Message
                };
            }
        }

        private async Task<ClientResponse> PostRequestResponseAsync(string url, StringContent content)
        {
            try
            {
                HttpClient client = httpClient.Value;

                using (var request = new HttpRequestMessage(HttpMethod.Post, url))
                { 
                    request.Headers.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                    request.Content = content;

                    using (var result = await client.SendAsync(request))
                    {
                        if (result.StatusCode == HttpStatusCode.OK)
                            return new ClientResponse()
                            {
                                ResponseCode = "200 - OK",
                                ResponseValue = result.Content.ReadAsStringAsync().Result
                            };
                        else
                            return new ClientResponse()
                            {
                                ResponseCode = result.StatusCode.ToString(),
                                ResponseValue = result.ReasonPhrase

                            };
                    }
                }
            }
            catch (Exception e)
            {
                return new ClientResponse()
                {
                    ResponseCode = string.Empty,
                    ResponseValue = e.Message
                };
            }
        }

        private async Task<ClientResponse> PutRequestResponseAsync(string url, StringContent content)
        {
            try
            {
                HttpClient client = httpClient.Value;

                using (var request = new HttpRequestMessage(HttpMethod.Put, url))
                {
                    request.Content = content;
                    request.Headers.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                    using (var result = await client.SendAsync(request))
                    {

                        if (result.StatusCode == HttpStatusCode.OK)
                            return new ClientResponse()
                            {
                                ResponseCode = "200 - OK",
                                ResponseValue = result.Content.ReadAsStringAsync().Result
                            };
                        else
                            return new ClientResponse()
                            {
                                ResponseCode = result.StatusCode.ToString(),
                                ResponseValue = result.ReasonPhrase
                            };

                    }
                }
            }
            catch (Exception e)
            {
                return new ClientResponse()
                {
                    ResponseCode = string.Empty,
                    ResponseValue = e.Message
                };
            }
        }
    }
}
