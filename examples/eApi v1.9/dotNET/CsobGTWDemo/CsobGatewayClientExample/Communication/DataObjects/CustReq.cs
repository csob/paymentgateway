using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class CustReq : SignBaseRequest
{
    public CustReq()
    {
    }

    public CustReq(string? merchantId, string? customerId)
    {
        MerchantId = merchantId;
        CustomerId = customerId;
    }

    [JsonProperty("customerId")] public string? CustomerId { get; set; }

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