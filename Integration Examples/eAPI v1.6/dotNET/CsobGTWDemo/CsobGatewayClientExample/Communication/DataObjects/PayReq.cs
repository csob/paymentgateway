using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects
{
    public class PayReq : SignBase, IBaseRequest
    {
        [JsonProperty("merchantId")]
        public string MerchantId { get; set; }

        [JsonProperty("payId")]
        public string PayId { get; set; }

        public PayReq()
        {

        }

        public PayReq(string merchantId, string payId)
        {
            this.MerchantId = merchantId;
            this.PayId = payId;
        }

        public virtual string ToSign()
        {
            StringBuilder sb = new StringBuilder();
            Add(sb, MerchantId);
            Add(sb, PayId);
            Add(sb, DateTime);
            DeleteLast(sb);
            return sb.ToString();
        }
    }
}
