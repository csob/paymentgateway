using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Communication.DataObjects;
using CsobGatewayClientExample.Security;
using ManyConsole;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class LoanLogistics : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public LoanLogistics()
    {
        IsCommand("LOAN_LOGISTICS", "Performs LOAN_LOGISTICS operation.");
        HasRequiredOption("p|payId=", "PayId obtained from LOAN_INIT", p => PayId = p);
        HasRequiredOption("e|event=",
            "Event: available options are delivered | picked_up | dispatched | ready_for_pickup",
            p => Event = p);
    }

    private string? PayId { get; set; }

    private string? Event { get; set; }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var loanLogisticsRequest = new LoanLogisticsRequest
        {
            PayId = PayId,
            Event = Event
        };
        var result = client.LoanLogistics(loanLogisticsRequest);
        _log.Info("result code: {}, result message: {}, payId: {}", result.ResultCode, result.ResultMessage,
            result.PayId);
        return 0;
    }
}