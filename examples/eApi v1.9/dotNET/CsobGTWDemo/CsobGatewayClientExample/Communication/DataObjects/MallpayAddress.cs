using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class MallpayAddress : ApiBase
{
    [JsonProperty("name")] public string? Name { get; set; }

    [JsonProperty("country")] public string? Country { get; set; }

    [JsonProperty("city")] public string? City { get; set; }

    [JsonProperty("streetAddress")] public string? StreetAddress { get; set; }

    [JsonProperty("streetNumber")] public string? StreetNumber { get; set; }

    [JsonProperty("zip")] public string? Zip { get; set; }

    [JsonProperty("addressType")] public string? AddressType { get; set; }


    public string? ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Name);
        Add(sb, Country);
        Add(sb, City);
        Add(sb, StreetAddress);
        Add(sb, StreetNumber);
        Add(sb, Zip);
        Add(sb, AddressType);
        DeleteLast(sb);
        return sb.ToString();
    }
}