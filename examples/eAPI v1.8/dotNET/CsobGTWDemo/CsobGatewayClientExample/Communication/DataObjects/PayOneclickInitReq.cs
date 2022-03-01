using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects
{
    public class PayOneclickInitReq : SignBaseRequest
    {
        [JsonProperty("origPayId")]
        public string OrigPayId { get; set; }

        [JsonProperty("orderNo")]
        public string OrderNo { get; set; }

        [JsonProperty("totalAmount")]
        public long TotalAmount { get; set; }

        [JsonProperty("currency")]
        public string Currency { get; set; }

        [JsonProperty("clientIp")]
        public string ClientIp { get; set; }

        public override string ToSign()
        {
            var sb = new StringBuilder();
            Add(sb, MerchantId);
            Add(sb, OrigPayId);
            Add(sb, OrderNo);
            Add(sb, DateTime);
            Add(sb, ClientIp);
            Add(sb, TotalAmount);
            Add(sb, Currency);
            DeleteLast(sb);
            return sb.ToString();
        }
    }
}
