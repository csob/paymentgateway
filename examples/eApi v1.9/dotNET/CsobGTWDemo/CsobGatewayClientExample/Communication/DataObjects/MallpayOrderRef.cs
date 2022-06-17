using System.Text;
using CsobGatewayClientExample.Communication.DataObjects.Ext;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class MallpayOrderRef : ApiBase
{
    [JsonProperty("totalPrice")] public MallpayPrice? TotalPrice { get; set; }

    [JsonProperty("totalVat")] public List<MallpayVat>? TotalVat { get; set; }

    [JsonProperty("items")] public List<MallpayOrderItemRef>? Items { get; set; }

    [JsonProperty("extensions")] public List<Extension>? Extensions { get; set; }


    public string ToSign()
    {
        var sb = new StringBuilder();
        if (TotalPrice != null) Add(sb, TotalPrice?.ToSign());
        if (TotalVat != null)
            foreach (var vat in TotalVat)
                Add(sb, vat.ToSign());
        if (Items != null)
            foreach (var item in Items)
                Add(sb, item.ToSign());
        DeleteLast(sb);
        return sb.ToString();
    }
}