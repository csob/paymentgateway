using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class MallpayInitRequest : SignBaseRequest
{
    [JsonProperty("orderNo")] public string? OrderNo { get; set; }

    [JsonProperty("customer")] public MallpayCustomer? Customer { get; set; }

    [JsonProperty("order")] public MallpayOrder? Order { get; set; }

    [JsonProperty("agreeTC")] public bool? AgreeTC { get; set; }

    [JsonProperty("clientIp")] public string? ClientIp { get; set; }

    [JsonProperty("returnUrl")] public string? ReturnUrl { get; set; }

    [JsonProperty("returnMethod")] public string? ReturnMethod { get; set; }

    [JsonProperty("merchantData")] public string? MerchantData { get; set; }

    [JsonProperty("ttlSec")] public long? TtlSec { get; set; }


    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, OrderNo);
        if (null != Customer) Add(sb, Customer.ToSign());
        if (null != Order) Add(sb, Order.ToSign());
        Add(sb, AgreeTC);
        Add(sb, Dttm);
        Add(sb, ClientIp);
        Add(sb, ReturnUrl);
        Add(sb, ReturnMethod);
        Add(sb, MerchantData);
        Add(sb, TtlSec);
        DeleteLast(sb);
        return sb.ToString();
    }
}