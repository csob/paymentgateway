using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Security;
using ManyConsole;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class OneclickEcho : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public OneclickEcho()
    {
        IsCommand("ONECLICK_ECHO", "Performs ONECLICK_ECHO operation.");
        HasRequiredOption("x|origPayId=", "Original pay Id.", p => OriginalPayId = p);
    }

    private string? OriginalPayId { get; set; }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var result = client.OneclickEcho(OriginalPayId ?? throw new InvalidOperationException());
        _log.Info("result code: {}, result message: {}", result.ResultCode, result.ResultMessage);
        return 0;
    }
}