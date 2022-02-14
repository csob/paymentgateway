using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects
{
    public class CartItem : ApiBase
    {
        [JsonProperty("name")]
        public string Name{ get; set; }

        [JsonProperty("quantity")]
        public int Quantity { get; set; }

        [JsonProperty("amount")]
        public long Amount { get; set; }

        [JsonProperty("description")]
        public string Description{ get; set; }

        public CartItem()
        {
        }

        public CartItem(string name, int quantity, long amount)
        {
            this.Name = name;
            this.Quantity = quantity;
            this.Amount = amount;
        }

        public CartItem(string name, int quantity, long amount, string description)
        {
            this.Name = name;
            this.Quantity = quantity;
            this.Amount = amount;
            this.Description = description;
        }

        public string ToSign()
        {
            StringBuilder sb = new StringBuilder();
            Add(sb, Name);
            Add(sb, Quantity);
            Add(sb, Amount);
            Add(sb, Description);
            DeleteLast(sb);
            return sb.ToString();
        }
    }
}
