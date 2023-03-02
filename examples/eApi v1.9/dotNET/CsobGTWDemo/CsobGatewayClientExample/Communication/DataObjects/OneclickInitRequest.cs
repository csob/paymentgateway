using System.Text;
using CsobGatewayClientExample.Communication.DataObjects.Ext;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class OneclickInitRequest : SignBaseRequest
{
    [JsonProperty("origPayId")] public string? OrigPayId { get; set; }

    [JsonProperty("orderNo")] public string? OrderNo { get; set; }

    [JsonProperty("totalAmount")] public long? TotalAmount { get; set; }

    [JsonProperty("currency")] public string? Currency { get; set; }

    [JsonProperty("clientIp")] public string? ClientIp { get; set; }


    [JsonProperty("closePayment")] public bool? ClosePayment { get; set; }

    [JsonProperty("returnUrl")] public string? ReturnUrl { get; set; }

    [JsonProperty("returnMethod")] public string? ReturnMethod { get; set; }

    [JsonProperty("customer")] public Customer? Customer { get; set; }

    [JsonProperty("order")] public Order? Order { get; set; }

    [JsonProperty("clientInitiated")] public bool? ClientInitiated { get; set; }

    [JsonProperty("sdkUsed")] public bool? SdkUsed { get; set; }

    [JsonProperty("merchantData")] public string? MerchantData { get; set; }

    [JsonProperty("extension")] private List<Extension>? Extensions { get; set; }

    [JsonProperty("ttlSec")] public int? TtlSec { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, OrigPayId);
        Add(sb, OrderNo);
        Add(sb, Dttm);
        Add(sb, ClientIp);
        Add(sb, TotalAmount);
        Add(sb, Currency);
        Add(sb, ClosePayment);
        Add(sb, ReturnUrl);
        Add(sb, ReturnMethod);
        if (null != Customer) Add(sb, Customer.ToSign());
        if (null != Order) Add(sb, Order.ToSign());
        Add(sb, ClientInitiated);
        Add(sb, SdkUsed);
        Add(sb, MerchantData);
	Add(sb, TtlSec);
        DeleteLast(sb);
        return sb.ToString();
    }
}