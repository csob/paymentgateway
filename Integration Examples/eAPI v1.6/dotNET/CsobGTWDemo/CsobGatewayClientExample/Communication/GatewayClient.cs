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

        public string MerchantId { get; set; }

        public async Task<ClientResponse> CallEchoGet() => await CallEcho(RequestType.Get);

        public async Task<ClientResponse> CallEchoPost() => await CallEcho(RequestType.Post);

        public async Task<ClientResponse> CallProcess(string payId) => await CallPaymenProcess(payId, "payment/process");

        public async Task<ClientResponse> CallStatus(string payId) => await CallPaymenProcess(payId, "payment/status");

        public async Task<ClientResponse> CallInit()
        {
            PayInitReq request;
            using (var file = File.OpenText(Constants.PaymentInitBaseFilePath))
                request = JsonConvert.DeserializeObject<PayInitReq>(file.ReadToEnd());

            request.DateTime = $"{DateTime.Now:yyyyMMddHHmmss}";
            request.MerchantId = MerchantId;
            request.Signature = Crypto.Sign(request.ToSign(), Constants.PrivateKeyFilePath);

            return await CreatePostRequest("payment/init", request);
        }

        public async Task<ClientResponse> CallReverse(string payId)
        {
            var request = new PayReq()
            {
                MerchantId = MerchantId,
                DateTime = $"{DateTime.Now:yyyyMMddHHmmss}",
                PayId = payId
            };
            request.Signature = Crypto.Sign(request.ToSign(), Constants.PrivateKeyFilePath);

            return await CreatePutRequest("payment/reverse", request);
        }

        public async Task<ClientResponse> CallRefund(string payId, long amount)
        {
            var request = new PayRefundReq()
            {
                MerchantId = MerchantId,
                DateTime = $"{DateTime.Now:yyyyMMddHHmmss}",
                PayId = payId,
                Amount = amount
            };
            request.Signature = Crypto.Sign(request.ToSign(), Constants.PrivateKeyFilePath);

            return await CreatePutRequest("payment/refund", request);
        }

        private async Task<ClientResponse> CallPaymenProcess(string payId, string method)
        {
            var request = new PayReq()
            {
                MerchantId = MerchantId,
                DateTime = $"{DateTime.Now:yyyyMMddHHmmss}",
                PayId = payId
            };
            request.Signature = Crypto.Sign(request.ToSign(), Constants.PrivateKeyFilePath);

            return await CreateGetRequest(method, request, true, (method != "payment/process"));
        }

        public async Task<ClientResponse> CallClose(string payId)
        {
            var request = new PayReq()
            {
                MerchantId = MerchantId,
                DateTime = $"{DateTime.Now:yyyyMMddHHmmss}",
                PayId = payId
            };
            request.Signature = Crypto.Sign(request.ToSign(), Constants.PrivateKeyFilePath);

            return await CreatePutRequest("payment/close", request);
        }

        private async Task<ClientResponse> CallEcho(RequestType type)
        {
            var request = new EchoRequest
            {
                MerchantId = MerchantId,
                DateTime = $"{DateTime.Now:yyyyMMddHHmmss}"
            };
            request.Signature = Crypto.Sign(request.ToSign(), Constants.PrivateKeyFilePath);

            if (type == RequestType.Get)
                return await CreateGetRequest("echo", request);
            else
                return await CreatePostRequest("echo", request);
        }

        public async Task<ClientResponse> CallOneClickInit(string payId)
        {
            PayOneclickInitReq request;
            using (var file = File.OpenText(Constants.PaymentOneclickBaseFilePath))
                request = JsonConvert.DeserializeObject<PayOneclickInitReq>(file.ReadToEnd());

            request.DateTime = $"{DateTime.Now:yyyyMMddHHmmss}";
            request.MerchantId = MerchantId;
            request.Signature = Crypto.Sign(request.ToSign(), Constants.PrivateKeyFilePath);

            return await CreatePostRequest("payment/oneclick/init", request);
        }

        public async Task<ClientResponse> CallOneClickStart(string payId)
        {
            var request = new PayReq()
            {
                MerchantId = MerchantId,
                DateTime = $"{DateTime.Now:yyyyMMddHHmmss}",
                PayId = payId
            };
            request.Signature = Crypto.Sign(request.ToSign(), Constants.PrivateKeyFilePath);

            return await CreatePostRequest("payment/oneclick/start", request);
        }

        public async Task<ClientResponse> CallCustomerInfo(string customerId)
        {
            var request = new CustReq()
            {
                MerchantId = MerchantId,
                DateTime = $"{DateTime.Now:yyyyMMddHHmmss}",
                CustomerId = customerId
            };
            request.Signature = Crypto.Sign(request.ToSign(), Constants.PrivateKeyFilePath);

            return await CreateGetRequest("customer/info", request);
        }

        private async Task<ClientResponse> CreatePostRequest(string method, IBaseRequest request)
        {
            var url = $"{Constants.GatewayUrl}/{method}";
            var content = new StringContent(JsonConvert.SerializeObject(request), Encoding.UTF8, "application/json");

            return await PostRequestResponse(url, content);
        }

        private async Task<ClientResponse> CreatePutRequest(string method, IBaseRequest request)
        {
            var url = $"{Constants.GatewayUrl}/{method}";
            var content = new StringContent(JsonConvert.SerializeObject(request), Encoding.UTF8, "application/json");

            return await PutRequestResponse(url, content);
        }

        private async Task<ClientResponse> CreateGetRequest(string method, EchoRequest request)
        {
            var url =
                $"{Constants.GatewayUrl}/" +
                $"{method}/" +
                $"{HttpUtility.UrlEncode(request.MerchantId)}/" +
                $"{request.DateTime}/" +
                $"{HttpUtility.UrlEncode(request.Signature, Encoding.UTF8)}";

            return await GetRequestResponse(url);
        }

        private async Task<ClientResponse> CreateGetRequest(string method, PayReq request, bool actLikeBrowser = false, bool autoRedirectHeader = true)
        {
            var url =
                $"{Constants.GatewayUrl}/" +
                $"{method}/" +
                $"{HttpUtility.UrlEncode(request.MerchantId)}/" +
                $"{request.PayId}/" +
                $"{request.DateTime}/" +
                $"{HttpUtility.UrlEncode(request.Signature, Encoding.UTF8)}";

            return await GetRequestResponse(url, actLikeBrowser, autoRedirectHeader);
        }

        private async Task<ClientResponse> CreateGetRequest(string method, CustReq request)
        {
            var url =
                $"{Constants.GatewayUrl}/" +
                $"{method}/" +
                $"{HttpUtility.UrlEncode(request.MerchantId)}/" +
                $"{request.CustomerId}/" +
                $"{request.DateTime}/" +
                $"{HttpUtility.UrlEncode(request.Signature, Encoding.UTF8)}";

            return await GetRequestResponse(url);
        }

        private async Task<ClientResponse> GetRequestResponse(string url, bool actLikeBrowser = false, bool autoRedirectHeader = true)
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

        private async Task<ClientResponse> PostRequestResponse(string url, StringContent content)
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

        private async Task<ClientResponse> PutRequestResponse(string url, StringContent content)
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
