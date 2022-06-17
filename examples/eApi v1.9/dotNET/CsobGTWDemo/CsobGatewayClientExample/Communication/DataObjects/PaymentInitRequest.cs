using System.Text;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class PaymentInitRequest : SignBaseRequest
{
    [JsonProperty("orderNo")] public string? OrderNo { get; set; }

    [JsonProperty("payOperation")] public string? PayOperation { get; set; }

    [JsonProperty("payMethod")] public string? PayMethod { get; set; }

    [JsonProperty("totalAmount")] public long TotalAmount { get; set; }

    [JsonConverter(typeof(StringEnumConverter))]
    [JsonProperty("currency")]
    public Cur? Currency { get; set; }

    [JsonProperty("closePayment")] public bool? ClosePayment { get; set; }

    [JsonProperty("returnUrl")] public string? ReturnUrl { get; set; }

    [JsonProperty("returnMethod")] public string? ReturnMethod { get; set; }


    [JsonProperty("customer")] private Customer? Customer { get; set; }

    [JsonProperty("order")] private Order? Order { get; set; }

    [JsonProperty("cart")] public List<CartItem>? Cart { get; set; }


    [JsonProperty("merchantData")] public string? MerchantData { get; set; }

    [JsonProperty("customerId")]
    public string? CustomerId { get; set; }

    [JsonConverter(typeof(StringEnumConverter))]
    [JsonProperty("language")]
    public Lang? Language { get; set; }

    [JsonProperty("ttlSec")] public int? TtlSec { get; set; }

    [JsonProperty("logoVersion")] public int? LogoVersion { get; set; }

    [JsonProperty("colorSchemeVersion")] public int? ColorSchemeVersion { get; set; }

    [JsonProperty("customExpiry")] public string? CustomExpiry { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, OrderNo);
        Add(sb, Dttm);
        Add(sb, PayOperation);
        Add(sb, PayMethod);
        Add(sb, TotalAmount);
        Add(sb, Currency.ToString());
        Add(sb, ClosePayment.ToString().ToLower());
        Add(sb, ReturnUrl);
        Add(sb, ReturnMethod);

        if (Cart != null)
            foreach (var item in Cart)
                Add(sb, item.ToSign());


        if (null != Customer) Add(sb, Customer.ToSign());
        if (null != Order) Add(sb, Order.ToSign());

        Add(sb, MerchantData);
        Add(sb, CustomerId);
        Add(sb, Language.ToString());
        Add(sb, TtlSec);
        Add(sb, LogoVersion);
        Add(sb, ColorSchemeVersion);
        Add(sb, CustomExpiry);
        DeleteLast(sb);
        return sb.ToString();
    }
}

public enum Lang
{
    cs,
    en,
    de,
    fr,
    hu,
    it,
    ja,
    pl,
    pt,
    ro,
    ru,
    sk,
    es,
    tr,
    vi,
    hr,
    sl,
    sv
}

public enum Cur
{
    CZK,
    EUR,
    USD,
    GBP,
    HUF,
    PLN,
    HRK,
    RON,
    NOK,
    SEK
}