using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class MallpayOrder : ApiBase
{
    [JsonProperty("totalPrice")] public MallpayPrice? TotalPrice { get; set; }

    [JsonProperty("totalVat")] public List<MallpayVat>? TotalVat { get; set; } = new();

    [JsonProperty("addresses")] public List<MallpayAddress>? Addresses { get; set; } = new();


    [JsonProperty("deliveryType")] public string? DeliveryType { get; set; }

    [JsonProperty("carrierId")] public string? CarrierId { get; set; }

    [JsonProperty("carrierCustom")] public string? CarrierCustom { get; set; }

    [JsonProperty("items")] public List<MallpayOrderItem>? Items { get; set; }


    public string ToSign()
    {
        var sb = new StringBuilder();
        if (TotalPrice != null) Add(sb, TotalPrice.ToSign());
        if (TotalVat != null)
            foreach (var vat in TotalVat)
                Add(sb, vat.ToSign());
        if (Addresses != null)
            foreach (var address in Addresses)
                Add(sb, address.ToSign());
        Add(sb, DeliveryType);
        Add(sb, CarrierId);
        Add(sb, CarrierCustom);
        if (Items != null)
            foreach (var item in Items)
                Add(sb, item.ToSign());
        DeleteLast(sb);
        return sb.ToString();
    }
}