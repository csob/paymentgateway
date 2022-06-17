using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class CartItem : ApiBase
{
    public CartItem()
    {
    }

    public CartItem(string? name, int quantity, long amount)
    {
        Name = name;
        Quantity = quantity;
        Amount = amount;
    }

    public CartItem(string? name, int quantity, long amount, string? description)
    {
        Name = name;
        Quantity = quantity;
        Amount = amount;
        Description = description;
    }

    [JsonProperty("name")] public string? Name { get; set; }

    [JsonProperty("quantity")] public int Quantity { get; set; }

    [JsonProperty("amount")] public long Amount { get; set; }

    [JsonProperty("description")] public string? Description { get; set; }

    public string? ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Name);
        Add(sb, Quantity);
        Add(sb, Amount);
        Add(sb, Description);
        DeleteLast(sb);
        return sb.ToString();
    }
}