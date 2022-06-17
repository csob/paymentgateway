using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class LoanInitRequest : SignBaseRequest
{
    [JsonProperty("orderNo")] public string? OrderNo { get; set; }

    [JsonProperty("purchaseId")] public string? PurchaseId { get; set; }

    [JsonProperty("clientIp")] public string? ClientIp { get; set; }

    [JsonProperty("totalAmount")] public long? TotalAmount { get; set; }

    [JsonProperty("currency")] public string? Currency { get; set; }

    [JsonProperty("returnUrl")] public string? ReturnUrl { get; set; }

    [JsonProperty("returnMethod")] public string? ReturnMethod { get; set; }

    [JsonProperty("customer")] public LoanCustomer? Customer { get; set; }

    [JsonProperty("merchantData")] public string? MerchantData { get; set; }

    [JsonProperty("language")] public string? Language { get; set; }


    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, OrderNo);
        Add(sb, PurchaseId);
        Add(sb, Dttm);
        Add(sb, ClientIp);
        Add(sb, TotalAmount);
        Add(sb, Currency);
        Add(sb, ReturnUrl);
        Add(sb, ReturnMethod);
        Add(sb, Customer?.ToSign());
        Add(sb, MerchantData);
        Add(sb, Language);
        DeleteLast(sb);
        return sb.ToString();
    }
}