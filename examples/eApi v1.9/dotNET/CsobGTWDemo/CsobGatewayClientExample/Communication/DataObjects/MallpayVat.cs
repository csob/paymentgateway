using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class MallpayVat : ApiBase
{
    [JsonProperty("amount")] public long Amount { get; set; }

    [JsonProperty("currency")] public string? Currency { get; set; }

    [JsonProperty("vatRate")] public long VatRate { get; set; }


    public string? ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Amount);
        Add(sb, Currency);
        Add(sb, VatRate);
        DeleteLast(sb);
        return sb.ToString();
    }
}