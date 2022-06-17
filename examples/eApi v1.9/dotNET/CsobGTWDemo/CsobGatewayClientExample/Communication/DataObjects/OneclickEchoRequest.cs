using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class OneclickEchoRequest : SignBaseRequest //OK
{
    public OneclickEchoRequest()
    {
    }

    public OneclickEchoRequest(string? merchantId, string? OrigPayId)
    {
        MerchantId = merchantId;
        this.OrigPayId = OrigPayId;
    }

    [JsonProperty("origPayId", Required = Required.Always)]
    public string? OrigPayId { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, OrigPayId);
        Add(sb, Dttm);
        DeleteLast(sb);
        return sb.ToString();
    }
}