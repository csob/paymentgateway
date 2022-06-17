using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class Order : ApiBase
{
    [JsonProperty("type")] public string? Type { get; set; } //[purchase, balance, prepaid, cash, check 

    [JsonProperty("availability")] public string? Availability { get; set; }

    [JsonProperty("delivery")]
    public string? Delivery { get; set; } //[shipping, shipping_verified, instore, digital, ticket, order 

    [JsonProperty("deliveryMode")] public string? DeliveryMode { get; set; } //[0, 1, 2, 3 

    [JsonProperty("deliveryEmail")] public string? DeliveryEmail { get; set; }

    [JsonProperty("nameMatch")] public bool NameMatch { get; set; }

    [JsonProperty("addressMatch")] public bool AddressMatch { get; set; }

    [JsonProperty("billing")] public Address? Billing { get; set; }

    [JsonProperty("shipping")] public Address? Shipping { get; set; }

    [JsonProperty("shippingAddedAt")] public string? ShippingAddedAt { get; set; }

    [JsonProperty("reorder")] public bool Reorder { get; set; }

    [JsonProperty("giftcards")] public GiftCards? Giftcards { get; set; }


    public string? ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Type);
        Add(sb, Availability);
        Add(sb, Delivery);
        Add(sb, DeliveryMode);
        Add(sb, DeliveryEmail);
        Add(sb, NameMatch);
        Add(sb, AddressMatch);
        if (Billing != null) Add(sb, Billing.ToSign());
        if (Shipping != null) Add(sb, Shipping.ToSign());
        Add(sb, ShippingAddedAt);
        Add(sb, Reorder);
        if (Giftcards != null) Add(sb, Giftcards.ToSign());
        DeleteLast(sb);
        return sb.ToString();
    }
}

public class GiftCards : SignBase
{
    [JsonProperty("totalAmount")] public long? TotalAmount { get; set; }

    [JsonProperty("currency")] public string? Currency { get; set; }

    [JsonProperty("quantity")] public int? Quantity { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, TotalAmount);
        Add(sb, Currency);
        Add(sb, Quantity);
        DeleteLast(sb);
        return sb.ToString();
    }
}