using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects
{
    public class PayRefundReq : PayReq
    {
        [JsonProperty("amount")]
        public long Amount { get; set; }

        public PayRefundReq()
        {

        }

        public PayRefundReq(string merchantId, string payId, long amount)
        {
            this.MerchantId = merchantId;
            this.PayId = payId;
            this.Amount = amount;
        }

        public override string ToSign()
        {
            StringBuilder sb = new StringBuilder();
            Add(sb, MerchantId);
            Add(sb, PayId);
            Add(sb, DateTime);
            Add(sb, Amount);
            DeleteLast(sb);
            return sb.ToString();
        }
    }
}
