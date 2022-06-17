using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class LoanLogisticsRequest : SignBaseRequest
{
    [JsonProperty("payId", Required = Required.Always)]
    public string? PayId { get; set; }

    [JsonProperty("event", Required = Required.Always)]
    public string? Event { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, PayId);
        Add(sb, Event);
        Add(sb, Dttm);
        DeleteLast(sb);
        return sb.ToString();
    }
}