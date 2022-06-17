using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class ApplepayInitRequest : SignBaseRequest
{
    [JsonProperty("orderNo")] public string? OrderNo { get; set; }

    [JsonProperty("payMethod")] public string? PayMethod { get; set; }

    [JsonProperty("clientIp")] public string? ClientIp { get; set; }

    [JsonProperty("totalAmount")] public long? TotalAmount { get; set; }

    [JsonProperty("currency")] public string? Currency { get; set; }

    [JsonProperty("closePayment")] public bool? ClosePayment { get; set; }

    [JsonProperty("payload")] public string? Payload { get; set; }

    [JsonProperty("returnUrl")] public string? ReturnUrl { get; set; }

    [JsonProperty("returnMethod")] public string? ReturnMethod { get; set; }

    [JsonProperty("customer")] public Customer? Customer { get; set; }

    [JsonProperty("order")] public Order? Order { get; set; }

    [JsonProperty("sdkUsed")] public bool? SdkUsed { get; set; }

    [JsonProperty("merchantData")] public string? MerchantData { get; set; }

    [JsonProperty("ttlSec")] public long? TtlSec { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, OrderNo);
        Add(sb, Dttm);
        Add(sb, PayMethod);
        Add(sb, ClientIp);
        Add(sb, TotalAmount);
        Add(sb, Currency);
        Add(sb, ClosePayment);
        Add(sb, Payload);
        Add(sb, ReturnUrl);
        Add(sb, ReturnMethod);
        Add(sb, Customer?.ToString());
        Add(sb, Order?.ToString());
        Add(sb, SdkUsed);
        Add(sb, MerchantData);
        Add(sb, TtlSec);
        DeleteLast(sb);
        return sb.ToString();
    }
}