using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class EchoCustomerRequest : SignBaseRequest
{
    [JsonProperty("customerId", Required = Required.Always)]
    public string? CustomerId { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, CustomerId);
        Add(sb, Dttm);
        DeleteLast(sb);
        return sb.ToString();
    }
}