using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Security;
using ManyConsole;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class EchoCustomer : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public EchoCustomer()
    {
        IsCommand("ECHO_CUSTOMER", "Performs ECHO_CUSTOMER operation.");
        HasRequiredOption("c|customerId=", "Customer ID.", p => CustomerId = p);
    }

    private string? CustomerId { get; set; }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var result = client.EchoCustomer(CustomerId ?? throw new InvalidOperationException());
        _log.Info("result code: {}, result message: {}", result.ResultCode, result.ResultMessage);
        return 0;
    }
}