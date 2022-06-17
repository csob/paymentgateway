using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Security;
using ManyConsole;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class ApplepayEcho : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public ApplepayEcho()
    {
        IsCommand("APPLEPAY_ECHO", "Performs APPLEPAY_ECHO operation.");
    }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var result = client.ApplepayEcho();
        _log.Info("result code: {}, result message: {}", result.ResultCode, result.ResultMessage);
        return 0;
    }
}