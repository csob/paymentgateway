using System.Text;
using CsobGatewayClientExample.Communication.DataObjects.Act;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class ApplepayProcessRequest : SignBaseRequest
{
    [JsonProperty("payId")] public string? PayId { get; set; }

    [JsonProperty("fingerprint")] public AuthData? Fingerprint { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, PayId);
        Add(sb, Dttm);
        Add(sb, Fingerprint?.ToSign());
        DeleteLast(sb);
        return sb.ToString();
    }
}