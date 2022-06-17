using CsobGatewayClientExample.Common;
using CsobGatewayClientExample.Communication;
using CsobGatewayClientExample.Communication.DataObjects;
using CsobGatewayClientExample.Communication.DataObjects.Act;
using CsobGatewayClientExample.Security;
using ManyConsole;
using NLog;

namespace CsobGatewayClientExample.Commands;

public class LoanCancel : ConsoleCommand
{
    private readonly Logger _log = LogManager.GetCurrentClassLogger();

    public LoanCancel()
    {
        IsCommand("LOAN_CANCEL", "Performs LOAN_CANCEL operation.");
        HasRequiredOption("p|payId=", "PayId obtained from LOAN_INIT", p => PayId = p);
        HasRequiredOption("r|reason=",
            "Reason: avaiable options are: aborted | other_payment | undeliverable | unavailable | abandoned | changed | unprocessed",
            p => Reason = p);
    }

    private string? PayId { get; set; }

    private string? Reason { get; set; }

    public override int Run(string[] remainingArguments)
    {
        var crypto = new Crypto(Constants.MerchantId, Constants.MerchantKeyFileName, Constants.MipsPublicKey);
        var client = new MipsConnector(crypto);
        var loanCancelRequest = new LoanCancelRequest();
        loanCancelRequest.PayId = PayId;
        loanCancelRequest.Reason = Reason;
        var result = client.LoanCancel(loanCancelRequest);
        _log.Info("result code: {}, result message: {}, payId: {}, paymentStatus: {}", result.ResultCode,
            result.ResultMessage, result.PayId, result.PaymentStatus);
        return 0;
    }

    private static AuthData CreateTestFingerprint()
    {
        var fingerprint = new AuthData
        {
            Browser = new Auth3dsBrowser
            {
                JavaEnabled = false,
                Language = "cs-CZ",
                ColorDepth = 24,
                ScreenHeight = 720,
                ScreenWidth = 1280,
                Timezone = -60,
                JavascriptEnabled = true,
                ChallengeWindowSize = "01",
                AcceptHeader = "application/json, text/plain, */*",
                UserAgent =
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36"
            }
        };
        return fingerprint;
    }
}