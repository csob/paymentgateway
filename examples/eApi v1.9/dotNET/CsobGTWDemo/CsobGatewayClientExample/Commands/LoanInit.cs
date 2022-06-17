using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Communication.DataObjects;
using CsobGatewayClientExample.Security;
using ManyConsole;
using Newtonsoft.Json;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class LoanInit : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public LoanInit()
    {
        IsCommand("LOAN_INIT", "Performs LOAN_INIT operation.");
        HasRequiredOption("i|initFile=", "InitFile", p => InitFileName = p);
        HasOption("a|amount=", "Amount", p => Amount = Convert.ToInt64(p));
        HasOption("p|purchaseId=", "PurchaseId", p => PurchaseId = p);
        HasOption("g|genPurchaseId", "generates random PurchaseId", p => PurchaseId = get_unique_string());
    }

    private string? InitFileName { get; set; }
    private long? Amount { get; set; }

    private string? PurchaseId { get; set; }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var initRequest =
            JsonConvert.DeserializeObject<LoanInitRequest>(
                File.ReadAllText(InitFileName ?? throw new InvalidOperationException()));
        if (null != Amount) initRequest.TotalAmount = Amount;
        if (null != PurchaseId) initRequest.PurchaseId = PurchaseId;
        var result = client.LoanInit(initRequest);
        _log.Info("result code: {}, result message: {}, payId: {}, paymentStatus: {}", result.ResultCode,
            result.ResultMessage, result.PayId, result.PaymentStatus);
        return 0;
    }

    private string get_unique_string()
    {
        return Convert.ToBase64String(Guid.NewGuid().ToByteArray()).Substring(0, 8);
    }
}