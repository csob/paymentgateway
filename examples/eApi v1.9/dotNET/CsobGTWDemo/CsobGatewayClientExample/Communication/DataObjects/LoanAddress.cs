using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class LoanAddress : ApiBase
{
    [JsonProperty("address1")] public string? Address1 { get; set; }

    [JsonProperty("address2")] public string? Address2 { get; set; }

    [JsonProperty("city")] public string? City { get; set; }

    [JsonProperty("zip")] public string? Zip { get; set; }

    [JsonProperty("country")] public string? Country { get; set; }

    [JsonProperty("type")] public string? Type { get; set; }


    public string? ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Address1);
        Add(sb, Address2);
        Add(sb, City);
        Add(sb, Zip);
        Add(sb, Country);
        Add(sb, Type);
        DeleteLast(sb);
        return sb.ToString();
    }
}