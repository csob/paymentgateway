using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Security;
using ManyConsole;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class GooglepayProcess : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public GooglepayProcess()
    {
        IsCommand("GOOGLEPAY_PROCESS", "Performs GOOGLEPAY_PROCESS operation.");
        HasRequiredOption("p|payId=", "PayId obtained from GOOGLEPAY_INIT", p => PayId = p);
    }

    private string? PayId { get; set; }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var result = client.GooglepayProcess(PayId ?? throw new InvalidOperationException());
        _log.Info("result code: {}, result message: {}, payId: {}, paymentStatus: {}", result.ResultCode,
            result.ResultMessage, result.PayId, result.PaymentStatus);
        return 0;
    }
}