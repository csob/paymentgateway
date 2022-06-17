using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class MallpayCustomer : ApiBase
{
    [JsonProperty("firstName")] public string? FirstName { get; set; }

    [JsonProperty("lastName")] public string? LastName { get; set; }

    [JsonProperty("fullName")] public string? FullName { get; set; }

    [JsonProperty("titleBefore")] public string? TitleBefore { get; set; }

    [JsonProperty("titleAfter")] public string? TitleAfter { get; set; }

    [JsonProperty("email")] public string? Email { get; set; }

    [JsonProperty("phone")] public string? Phone { get; set; }

    [JsonProperty("tin")] public string? Tin { get; set; }

    [JsonProperty("vatin")] public string? Vatin { get; set; }


    public string? ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, FirstName);
        Add(sb, LastName);
        Add(sb, FullName);
        Add(sb, TitleBefore);
        Add(sb, TitleAfter);
        Add(sb, Email);
        Add(sb, Phone);
        Add(sb, Tin);
        Add(sb, Vatin);
        DeleteLast(sb);
        return sb.ToString();
    }
}