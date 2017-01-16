using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects
{

    public class EchoRequest : SignBase, IBaseRequest
    {
        [JsonProperty("merchantId")]
        public string MerchantId { get; set; }

        public string ToSign()
        {
            var sb = new StringBuilder();
            Add(sb, MerchantId);
            Add(sb, DateTime);
            DeleteLast(sb);
            return sb.ToString();
        }
    }
}
