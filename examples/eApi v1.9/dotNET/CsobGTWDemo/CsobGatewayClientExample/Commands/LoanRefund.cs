using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Communication.DataObjects;
using CsobGatewayClientExample.Security;
using ManyConsole;
using Newtonsoft.Json;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class LoanRefund : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public LoanRefund()
    {
        IsCommand("LOAN_REFUND", "Performs LOAN_REFUND operation.");
        HasRequiredOption("i|initFile=", "InitFile", p => InitFileName = p);
        HasOption("p|payId=", "PayId from LOAN_INIT request", p => PayId = p);
    }

    private string? InitFileName { get; set; }
    private string? PayId { get; set; }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var initRequest =
            JsonConvert.DeserializeObject<LoanRefundRequest>(
                File.ReadAllText(InitFileName ?? throw new InvalidOperationException()));
        if (null != PayId) initRequest.PayId = PayId;
        var result = client.LoanRefund(initRequest);
        _log.Info("result code: {}, result message: {}, payId: {}, paymentStatus: {}", result.ResultCode,
            result.ResultMessage, result.PayId, result.PaymentStatus);
        return 0;
    }
}