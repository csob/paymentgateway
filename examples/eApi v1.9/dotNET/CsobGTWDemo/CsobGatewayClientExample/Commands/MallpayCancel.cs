using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Communication.DataObjects;
using CsobGatewayClientExample.Security;
using ManyConsole;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class MallpayCancel : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public MallpayCancel()
    {
        IsCommand("MALLPAY_CANCEL", "Performs MALLPAY_CANCEL operation.");
        HasRequiredOption("p|payId=", "PayId obtained from LOAN_INIT", p => PayId = p);
        HasRequiredOption("r|reason=",
            "Reason: available options are: aborted | other_payment | undeliverable | unavailable | abandoned | changed | unprocessed",
            p => Reason = p);
    }

    private string? PayId { get; set; }

    private string? Reason { get; set; }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var loanCancelRequest = new MallpayCancelRequest
        {
            PayId = PayId,
            Reason = Reason
        };
        var result = client.MallpayCancel(loanCancelRequest);
        _log.Info("result code: {}, result message: {}, payId: {}, paymentStatus: {}", result.ResultCode,
            result.ResultMessage, result.PayId, result.PaymentStatus);
        return 0;
    }
}