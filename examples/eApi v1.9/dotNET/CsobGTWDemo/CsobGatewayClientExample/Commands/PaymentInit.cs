using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Communication.DataObjects;
using CsobGatewayClientExample.Security;
using ManyConsole;
using Newtonsoft.Json;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class PaymentInit : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public PaymentInit()
    {
        IsCommand("PAYMENT_INIT", "Performs PAYMENT_INIT operation.");
        HasRequiredOption("i|initFile=", "InitFile", p => InitFileName = p);
    }

    private string? InitFileName { get; set; }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var initRequest =
            JsonConvert.DeserializeObject<PaymentInitRequest>(
                File.ReadAllText(InitFileName ?? throw new InvalidOperationException()));
        var result = client.PaymentInit(initRequest);
        _log.Info("result code: {}, result message: {}, payId: {}, paymentStatus: {}", result.ResultCode,
            result.ResultMessage, result.PayId, result.PaymentStatus);
        return 0;
    }

    private string get_unique_string()
    {
        return Convert.ToBase64String(Guid.NewGuid().ToByteArray()).Substring(0, 8);
    }
}