using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects
{
    public class CustReq : SignBaseRequest
    {
        [JsonProperty("customerId")]
        public string CustomerId { get; set; }

        public CustReq()
        {

        }

        public CustReq(string merchantId, string customerId)
        {
            this.MerchantId = merchantId;
            this.CustomerId = customerId;
        }
        
        public override string ToSign()
        {
            var sb = new StringBuilder();
            Add(sb, MerchantId);
            Add(sb, CustomerId);
            Add(sb, DateTime);
            DeleteLast(sb);
            return sb.ToString();
        }
    }
}
