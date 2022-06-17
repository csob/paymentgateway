using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class MallpayOrderItem : ApiBase
{
    [JsonProperty("code")] public string? Code { get; set; }

    [JsonProperty("ean")] public string? Ean { get; set; }

    [JsonProperty("name")] public string? Name { get; set; }

    [JsonProperty("type")] public string? Type { get; set; }

    [JsonProperty("quantity")] public long? Quantity { get; set; }

    [JsonProperty("variant")] public string? Variant { get; set; }

    [JsonProperty("description")] public string? Description { get; set; }

    [JsonProperty("producer")] public string? Producer { get; set; }

    [JsonProperty("categories")] public List<string>? Categories { get; set; }

    [JsonProperty("unitPrice")] public MallpayPrice? UnitPrice { get; set; }

    [JsonProperty("unitVat")] public MallpayVat? UnitVat { get; set; }

    [JsonProperty("totalPrice")] public MallpayPrice? TotalPrice { get; set; }

    [JsonProperty("totalVat")] public MallpayVat? TotalVat { get; set; }

    [JsonProperty("productUrl")] public string? ProductUrl { get; set; }


    public string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Code);
        Add(sb, Ean);
        Add(sb, Name);
        Add(sb, Type);
        Add(sb, Quantity);
        Add(sb, Variant);
        Add(sb, Description);
        Add(sb, Producer);
        if (Categories != null)
            foreach (var category in Categories)
                Add(sb, category);
        if (UnitPrice != null) Add(sb, UnitPrice?.ToSign());
        if (UnitVat != null) Add(sb, UnitVat?.ToSign());
        if (TotalPrice != null) Add(sb, TotalPrice?.ToSign());
        if (TotalVat != null) Add(sb, TotalVat?.ToSign());
        Add(sb, ProductUrl);
        DeleteLast(sb);
        return sb.ToString();
    }
}