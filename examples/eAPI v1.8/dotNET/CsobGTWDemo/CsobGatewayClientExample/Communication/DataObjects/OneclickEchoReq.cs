using System.Text;
using Newtonsoft.Json;
namespace CsobGatewayClientExample.Communication.DataObjects
{
    public class OneclickEchoReq : SignBaseRequest
    {
        [JsonProperty("origPayId")]
        public string OrigPayId { get; set; }

        public OneclickEchoReq()
        {

        }

        public OneclickEchoReq(string merchantId, string OrigPayId)
        {
            this.MerchantId = merchantId;
            this.OrigPayId = OrigPayId;
        }
        
        public override string ToSign()
        {
            var sb = new StringBuilder();
            Add(sb, MerchantId);
            Add(sb, OrigPayId);
            Add(sb, DateTime);
            DeleteLast(sb);
            return sb.ToString();
        }
    }
}
