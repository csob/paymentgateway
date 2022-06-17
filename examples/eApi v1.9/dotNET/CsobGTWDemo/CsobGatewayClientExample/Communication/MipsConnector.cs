using System.Net.Http.Headers;
using System.Text;
using System.Web;
using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication.DataObjects;
using CsobGatewayClientExample.Communication.DataObjects.Act;
using CsobGatewayClientExample.Communication.DataObjects.Response;
using CsobGatewayClientExample.Security;
using Newtonsoft.Json;
using NLog;

namespace CsobGatewayClientExample.Communication;

public class MipsConnector
{
    private const string PAYMENT_STATUS = "/payment/status";
    private const string PAYMENT_REVERSE = "/payment/reverse";
    private const string PAYMENT_PROCESS = "/payment/process";
    private const string PAYMENT_CLOSE = "/payment/close";
    private const string PAYMENT_REFUND = "/payment/refund";
    private const string PAYMENT_INIT = "/payment/init";
    private const string ONECLICK_ECHO = "/oneclick/echo";
    private const string ONECLICK_INIT = "/oneclick/init";
    private const string ONECLICK_PROCESS = "/oneclick/process";
    private const string ECHO = "/echo";
    private const string ECHO_CUSTOMER = "/echo/customer";
    private const string BUTTON_INIT = "/button/init";
    private const string GOOGLEPAY_INIT = "/googlepay/init";
    private const string GOOGLEPAY_ECHO = "/googlepay/echo";
    private const string GOOGLEPAY_PROCESS = "/googlepay/process";
    private const string APPLEPAY_ECHO = "/applepay/echo";
    private const string APPLEPAY_INIT = "/applepay/init";
    private const string APPLEPAY_PROCESS = "/applepay/process";
    private const string LOAN_INIT = "/loan/init";
    private const string LOAN_LOGISTICS = "/loan/logistics";
    private const string LOAN_CANCEL = "/loan/cancel";
    private const string LOAN_REFUND = "/loan/refund";
    private const string MALLPAY_INIT = "/mallpay/init";
    private const string MALLPAY_LOGISTICS = "/mallpay/logistics";
    private const string MALLPAY_CANCEL = "/mallpay/cancel";
    private const string MALLPAY_REFUND = "/mallpay/refund";
    private readonly Crypto _cryptoService;
    private readonly Lazy<HttpClient> _httpClient = new(CreateHttpClient);

    private readonly Logger _log = LogManager.GetCurrentClassLogger();


    public MipsConnector(Crypto cryptoService)
    {
        _cryptoService = cryptoService;
    }

    private void FillAndSign(SignBaseRequest request)
    {
        request.MerchantId = _cryptoService.MerchantId;
        request.FillDateTime();
        request.Signature = _cryptoService.Sign(request.ToSign());
    }

    private static HttpClient CreateHttpClient()
    {
        var httpClientHandler = new HttpClientHandler();
        httpClientHandler.AllowAutoRedirect = false;
        var client = new HttpClient(httpClientHandler);
        return client;
    }


    public EchoResponse EchoGet()
    {
        var request = new EchoRequest();
        FillAndSign(request);
        var url =
            $"{Constants.GatewayUrl}" +
            $"{ECHO}/" +
            $"{HttpUtility.UrlEncode(request.MerchantId)}/" +
            $"{request.Dttm}/" +
            $"{HttpUtility.UrlEncode(request.Signature, Encoding.UTF8)}";
        return Request<EchoResponse>(url);
    }

    public EchoResponse EchoPost()
    {
        var request = new EchoRequest();
        const string url = $"{Constants.GatewayUrl}" +
                           $"{ECHO}";
        return Request
            <EchoResponse, EchoRequest>(url, request);
    }

    public EchoCustomerResponse EchoCustomer(string customerId)
    {
        var request = new EchoCustomerRequest
        {
            CustomerId = customerId
        };
        const string url =
            $"{Constants.GatewayUrl}" +
            $"{ECHO_CUSTOMER}";
        return Request<EchoCustomerResponse, EchoCustomerRequest>(url, request);
    }

    public OneclickEchoResponse OneclickEcho(string origPayId)
    {
        var request = new OneclickEchoRequest
        {
            OrigPayId = origPayId
        };
        const string url = $"{Constants.GatewayUrl}" +
                           $"{ONECLICK_ECHO}";
        return Request<OneclickEchoResponse, OneclickEchoRequest>(url, request);
    }

    public OneclickInitResponse OneclickInit(OneclickInitRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{ONECLICK_INIT}";
        return Request<OneclickInitResponse, OneclickInitRequest>(url, request);
    }

    public ProcessResponse OneclickProcess(string payId, AuthData fingerprint)
    {
        var request = new OneclickProcessRequest
        {
            PayId = payId,
            Fingerprint = fingerprint
        };
        const string url = $"{Constants.GatewayUrl}" +
                           $"{ONECLICK_PROCESS}";
        return Request<ProcessResponse, OneclickProcessRequest>(url, request);
    }

    public ButtonInitResponse ButtonInit(ButtonInitRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{BUTTON_INIT}";
        return Request<ButtonInitResponse, ButtonInitRequest>(url, request);
    }

    public GooglepayEchoResponse GooglepayEcho()
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{GOOGLEPAY_ECHO}";
        var request = new GooglepayEchoRequest();
        return Request<GooglepayEchoResponse, GooglepayEchoRequest>(url, request);
    }

    public GooglepayInitResponse GooglepayInit(GooglepayInitRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{GOOGLEPAY_INIT}";
        return Request<GooglepayInitResponse, GooglepayInitRequest>(url, request);
    }

    public ProcessResponse GooglepayProcess(string payId)
    {
        var request = new GooglepayProcessRequest
        {
            PayId = payId
        };
        const string url = $"{Constants.GatewayUrl}" +
                           $"{GOOGLEPAY_PROCESS}";
        return Request<ProcessResponse, GooglepayProcessRequest>(url, request);
    }

    public ApplepayEchoResponse ApplepayEcho()
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{APPLEPAY_ECHO}";
        var request = new ApplepayEchoRequest();
        return Request<ApplepayEchoResponse, ApplepayEchoRequest>(url, request);
    }

    public ApplepayInitResponse ApplepayInit(ApplepayInitRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{APPLEPAY_INIT}";
        return Request<ApplepayInitResponse, ApplepayInitRequest>(url, request);
    }

    public ProcessResponse ApplepayProcess(string payId, AuthData fingerprint)
    {
        var request = new ApplepayProcessRequest
        {
            PayId = payId,
            Fingerprint = fingerprint
        };
        const string url = $"{Constants.GatewayUrl}" +
                           $"{APPLEPAY_PROCESS}";
        return Request<ProcessResponse, ApplepayProcessRequest>(url, request);
    }

    public LoanInitResponse LoanInit(LoanInitRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{LOAN_INIT}";
        return Request<LoanInitResponse, LoanInitRequest>(url, request);
    }

    public ProcessResponse LoanCancel(LoanCancelRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{LOAN_CANCEL}";
        return PutRequest<ProcessResponse, LoanCancelRequest>(url, request);
    }

    public PaymentResponse LoanLogistics(LoanLogisticsRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{LOAN_LOGISTICS}";
        FillAndSign(request);
        return PutRequest<PaymentResponse, LoanLogisticsRequest>(url, request);
    }

    public PaymentResponse LoanRefund(LoanRefundRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{LOAN_REFUND}";
        return PutRequest<PaymentResponse, LoanRefundRequest>(url, request);
    }

    public MallpayInitResponse MallpayInit(MallpayInitRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{MALLPAY_INIT}";
        return Request<MallpayInitResponse, MallpayInitRequest>(url, request);
    }

    public PaymentResponse MallpayCancel(MallpayCancelRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{MALLPAY_CANCEL}";
        return PutRequest<PaymentResponse, MallpayCancelRequest>(url, request);
    }

    public PaymentResponse MallpayRefund(MallpayRefundRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{MALLPAY_REFUND}";
        return PutRequest<PaymentResponse, MallpayRefundRequest>(url, request);
    }

    public PaymentResponse MallpayLogistics(MallpayLogisticsRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{MALLPAY_LOGISTICS}";
        return PutRequest<PaymentResponse, MallpayLogisticsRequest>(url, request);
    }

    public PaymentInitResponse PaymentInit(PaymentInitRequest request)
    {
        const string url = $"{Constants.GatewayUrl}" +
                           $"{PAYMENT_INIT}";
        return Request<PaymentInitResponse, PaymentInitRequest>(url, request);
    }

    public PaymentResponse PaymentReverse(string payId)
    {
        var request = new PaymentReverseRequest
        {
            PayId = payId
        };
        const string url = $"{Constants.GatewayUrl}" +
                           $"{PAYMENT_REVERSE}";
        return PutRequest<PaymentResponse, PaymentReverseRequest>(url, request);
    }
    
    public PaymentResponse PaymentRefund(string payId, long? amount)
    {
        var request = new PaymentRefundRequest()
        {
            PayId = payId,
            Amount = amount
        };
        
        const string url = $"{Constants.GatewayUrl}" +
                           $"{PAYMENT_REFUND}";
        return PutRequest<PaymentResponse, PaymentRefundRequest>(url, request);
    }
    
    public PaymentResponse PaymentClose(string payId, long? totalAmount)
    {
        var request = new PaymentCloseRequest()
        {
            PayId = payId,
            TotalAmount = totalAmount
        };
        
        const string url = $"{Constants.GatewayUrl}" +
                           $"{PAYMENT_CLOSE}";
        return PutRequest<PaymentResponse, PaymentCloseRequest>(url, request);
    }
    
    public PaymentStatusResponse PaymentStatus(string payId)
    {
        var request = new PaymentStatusRequest
        {
            PayId = payId
        };
        FillAndSign(request);
        var url =
            $"{Constants.GatewayUrl}" +
            $"{PAYMENT_STATUS}/" +
            $"{HttpUtility.UrlEncode(request.MerchantId)}/" +
            $"{HttpUtility.UrlEncode(request.PayId)}/" +
            $"{request.Dttm}/" +
            $"{HttpUtility.UrlEncode(request.Signature, Encoding.UTF8)}";
        return Request<PaymentStatusResponse>(url);
    }
    private TR Request<TR>(string url) where TR : SignBase
    {
        _log.Debug("url: {}", url);
        var httpRequest = new HttpRequestMessage(HttpMethod.Get, url);
        var response = _httpClient.Value.SendAsync(httpRequest);
        var result = response.Result.Content.ReadAsStringAsync()
            .ContinueWith(task =>
            {
                _log.Debug("Response: {}", task.Result);
                return JsonConvert.DeserializeObject<TR>(task.Result);
            }).Result;
        if (!_cryptoService.Verify(result.ToSign(), result.Signature ?? throw new InvalidOperationException()))
            throw new Exception("Invalid signature");
        return result;
    }
    
    

    private TR Request<TR, TD>(string url, TD request) where TR : SignBase where TD : SignBaseRequest
    {
        return Request<TR,TD>(HttpMethod.Post, url, request);
    }

    private TR PutRequest<TR, TD>(string url, TD request) where TR : SignBase where TD : SignBaseRequest
    {
        return Request<TR,TD>(HttpMethod.Put, url, request);
    }
    
    private TR Request<TR, TD>(HttpMethod method, string url, TD request) where TR : SignBase where TD : SignBaseRequest
    {
        _log.Debug("url: {}", url);
        FillAndSign(request);
        var httpRequest = new HttpRequestMessage(method, url);
        httpRequest.Headers.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        _log.Debug("Request: {}", JsonConvert.SerializeObject(request));
        var content = new StringContent(JsonConvert.SerializeObject(request), Encoding.UTF8, "application/json");
        httpRequest.Content = content;
        var response = _httpClient.Value.SendAsync(httpRequest);
        var result = response.Result.Content.ReadAsStringAsync().ContinueWith(task =>
        {
            _log.Debug("Response: {}", task.Result);
            return JsonConvert.DeserializeObject<TR>(task.Result);
        }).Result;
        if (!_cryptoService.Verify(result.ToSign(), result.Signature ?? throw new InvalidOperationException()))
            throw new Exception("Invalid signature");
        return result;
    }
}