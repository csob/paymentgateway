using System.Text;
using CsobGatewayClientExample.Communication.DataObjects;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication;

public class PaymentCloseRequest : SignBaseRequest
{
    [JsonProperty("payId")]
    public string? PayId;
    [JsonProperty("totalAmount")]
    public long? TotalAmount;

    public override string ToSign()
    {
        StringBuilder sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, PayId);
        Add(sb, Dttm);
        Add(sb, TotalAmount);
        DeleteLast(sb);
        return sb.ToString();
    }
}