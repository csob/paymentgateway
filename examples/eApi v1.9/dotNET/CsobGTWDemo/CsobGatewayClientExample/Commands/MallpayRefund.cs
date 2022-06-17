using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Communication.DataObjects;
using CsobGatewayClientExample.Security;
using ManyConsole;
using Newtonsoft.Json;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class MallpayRefund : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public MallpayRefund()
    {
        IsCommand("MALLPAY_REFUND", "Performs MALLPAY_REFUND operation.");
        HasRequiredOption("i|initFile=", "InitFile", p => InitFileName = p);
        HasOption("p|payId=", "PayId", p => PayId = p);
    }

    private string? InitFileName { get; set; }

    private string? PayId { get; set; }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var initRequest =
            JsonConvert.DeserializeObject<MallpayRefundRequest>(
                File.ReadAllText(InitFileName ?? throw new InvalidOperationException()));
        if (null != PayId) initRequest.PayId = PayId;
        var result = client.MallpayRefund(initRequest);
        _log.Info("result code: {}, result message: {}, payId: {}, paymentStatus: {}", result.ResultCode,
            result.ResultMessage, result.PayId, result.PaymentStatus);
        return 0;
    }

    private string get_unique_string()
    {
        return Convert.ToBase64String(Guid.NewGuid().ToByteArray()).Substring(0, 8);
    }
}