using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class PaymentRefundRequest : PayReq
{
 
   [JsonProperty("amount")] public long? Amount { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, PayId);
        Add(sb, Dttm);
        Add(sb, Amount);
        DeleteLast(sb);
        return sb.ToString();
    }
}