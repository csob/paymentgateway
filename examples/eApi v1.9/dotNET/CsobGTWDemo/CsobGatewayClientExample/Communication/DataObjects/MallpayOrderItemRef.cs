using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class MallpayOrderItemRef : ApiBase
{
    [JsonProperty("code")] public string? Code { get; set; }

    [JsonProperty("ean")] public string? Ean { get; set; }

    [JsonProperty("name")] public string? Name { get; set; }

    [JsonProperty("type")] public string? Type { get; set; }

    [JsonProperty("quantity")] public long Quantity { get; set; }


    public string? ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Code);
        Add(sb, Ean);
        Add(sb, Name);
        Add(sb, Type);
        Add(sb, Quantity);
        DeleteLast(sb);
        return sb.ToString();
    }
}