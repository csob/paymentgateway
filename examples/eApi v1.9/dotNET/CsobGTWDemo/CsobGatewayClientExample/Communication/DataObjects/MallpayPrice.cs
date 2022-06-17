using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class MallpayPrice : ApiBase
{
    [JsonProperty("amount")] public long Amount { get; set; }


    [JsonProperty("currency")] public string? Currency { get; set; }


    public string? ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Amount);
        Add(sb, Currency);
        DeleteLast(sb);
        return sb.ToString();
    }
}