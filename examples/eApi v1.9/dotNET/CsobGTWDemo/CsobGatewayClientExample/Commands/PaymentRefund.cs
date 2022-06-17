using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Communication.DataObjects;
using CsobGatewayClientExample.Communication.DataObjects.Act;
using CsobGatewayClientExample.Security;
using ManyConsole;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class PaymentRefund : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public PaymentRefund()
    {
        IsCommand("PAYMENT_REFUND", "Performs PAYMENT_REFUND operation.");
        HasRequiredOption("p|payId=", "PayId obtained from PAYMENT_INIT", p => PayId = p);
        HasOption("a|amount=", "Amount", p => Amount = Convert.ToInt64(p));

    }

    private string? PayId { get; set; }
    private long? Amount { get; set; }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var result = client.PaymentRefund(PayId ?? throw new InvalidOperationException(), Amount);
        _log.Info("result code: {}, result message: {}, payId: {}, paymentStatus: {}", result.ResultCode,
            result.ResultMessage, result.PayId, result.PaymentStatus);
        return 0;
    }
}