using System.Text;
using CsobGatewayClientExample.Communication.DataObjects.Ext;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class MallpayRefundRequest : SignBaseRequest
{
    [JsonProperty("extension")] public List<Extension>? Extensions;


    [JsonProperty("payId")] public string? PayId { get; set; }

    [JsonProperty("amount")] public long? Amount { get; set; }

    [JsonProperty("refundedItems", Required = Required.Always)]
    public List<MallpayOrderItemRef>? RefundedItems { get; set; }


    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, PayId);
        Add(sb, Dttm);
        Add(sb, Amount);
        if (RefundedItems != null)
            foreach (var item in RefundedItems)
                Add(sb, item.ToSign());
        DeleteLast(sb);
        return sb.ToString();
    }
}