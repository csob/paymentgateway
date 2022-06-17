using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class LoanCustomer : ApiBase
{
    [JsonProperty("firstName")] public string? FirstName { get; set; }


    [JsonProperty("lastName")] public string? LastName { get; set; }


    [JsonProperty("email")] public string? Email { get; set; }


    [JsonProperty("phone")] public string? Phone { get; set; }


    [JsonProperty("billingAddress")] public LoanAddress? BillingAddress { get; set; }


    [JsonProperty("shippingAddress")] public LoanAddress? ShippingAddress { get; set; }


    public string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, FirstName);
        Add(sb, LastName);
        Add(sb, Email);
        Add(sb, Phone);
        Add(sb, BillingAddress?.ToSign());
        Add(sb, ShippingAddress?.ToSign());
        DeleteLast(sb);
        return sb.ToString();
    }
}