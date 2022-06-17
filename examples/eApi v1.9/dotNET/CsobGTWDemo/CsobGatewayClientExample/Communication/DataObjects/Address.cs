using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class Address : ApiBase
{
    [JsonProperty("address1")] public string? Address1 { get; set; }

    [JsonProperty("address2")] public string? Address2 { get; set; }

    [JsonProperty("address3")] public string? Address3 { get; set; }

    [JsonProperty("city")] public string? City { get; set; }

    [JsonProperty("zip")] public string? Zip { get; set; }

    [JsonProperty("state")] public string? State { get; set; }

    [JsonProperty("country")] public string? Country { get; set; }


    public string? ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Address1);
        Add(sb, Address2);
        Add(sb, Address3);
        Add(sb, City);
        Add(sb, Zip);
        Add(sb, State);
        Add(sb, Country);
        DeleteLast(sb);
        return sb.ToString();
    }
}