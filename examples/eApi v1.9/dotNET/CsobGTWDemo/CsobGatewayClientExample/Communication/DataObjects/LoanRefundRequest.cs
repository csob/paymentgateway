using System.Text;
using CsobGatewayClientExample.Communication.DataObjects.Ext;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class LoanRefundRequest : SignBaseRequest
{
    [JsonProperty("payId")] public string? PayId { get; set; }

    [JsonProperty("reason")] public string? Reason { get; set; }

    [JsonProperty("date")] public string? Date { get; set; }

    [JsonProperty("amount")] public long? Amount { get; set; }

    [JsonProperty("extensions")] public List<Extension>? Extensions { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();

        Add(sb, MerchantId);
        Add(sb, PayId);
        Add(sb, Dttm);
        Add(sb, Amount);
        Add(sb, Reason);
        Add(sb, Date);
        DeleteLast(sb);
        return sb.ToString();
    }
}