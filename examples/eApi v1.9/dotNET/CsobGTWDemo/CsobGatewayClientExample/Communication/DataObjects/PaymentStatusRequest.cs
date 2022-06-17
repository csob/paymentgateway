using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class PaymentStatusRequest : SignBaseRequest
{
    [JsonProperty("payId")] 
    public string? PayId { get; set; }
    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, PayId);
        Add(sb, Dttm);
        DeleteLast(sb);
        return sb.ToString();
    }
}