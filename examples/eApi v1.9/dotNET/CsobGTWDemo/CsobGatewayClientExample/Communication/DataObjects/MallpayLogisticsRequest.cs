using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class MallpayLogisticsRequest : SignBaseRequest
{
    [JsonProperty("payId")] public string? PayId { get; set; }

    [JsonProperty("date")] public string? Date { get; set; }

    [JsonProperty("fulfilled")] public MallpayOrderRef? Fulfilled { get; set; }

    [JsonProperty("cancelled")] public MallpayOrderRef? Cancelled { get; set; }

    [JsonProperty("deliveryTrackingNumber")]
    public string? DeliveryTrackingNumber { get; set; }

    [JsonProperty("event")] public string? Event { get; set; }


    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, PayId);
        Add(sb, Event);
        Add(sb, Date);
        Add(sb, Fulfilled?.ToSign());
        Add(sb, Cancelled?.ToSign());
        Add(sb, DeliveryTrackingNumber);
        Add(sb, Dttm);
        DeleteLast(sb);
        return sb.ToString();
    }
}