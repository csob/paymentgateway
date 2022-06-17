using System.Text;
using CsobGatewayClientExample.Communication.DataObjects.Ext;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class ButtonInitRequest : SignBaseRequest
{
    [JsonProperty("orderNo")] public string? OrderNo { get; set; }

    [JsonProperty("clientIp")] public string? ClientIp { get; set; }

    [JsonProperty("totalAmount")] public long? TotalAmount { get; set; }

    [JsonProperty("currency")] public string? Currency { get; set; }

    [JsonProperty("returnUrl")] public string? ReturnUrl { get; set; }

    [JsonProperty("returnMethod")] public string? ReturnMethod { get; set; }

    [JsonProperty("brand")] public string? Brand { get; set; }

    [JsonProperty("merchantData")] public string? MerchantData { get; set; }

    [JsonProperty("language")] public string? Language { get; set; }

    [JsonProperty("extensions")] public List<Extension>? Extensions { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, OrderNo);
        Add(sb, Dttm);
        Add(sb, ClientIp);
        Add(sb, TotalAmount);
        Add(sb, Currency);
        Add(sb, ReturnUrl);
        Add(sb, ReturnMethod);
        Add(sb, Brand);
        Add(sb, MerchantData);
        Add(sb, Language);
        DeleteLast(sb);
        return sb.ToString();
    }
}