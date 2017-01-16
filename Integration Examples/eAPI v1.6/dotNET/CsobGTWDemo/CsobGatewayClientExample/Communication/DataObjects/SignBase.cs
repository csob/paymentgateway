using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects
{
    public class SignBase : ApiBase
    {
        [JsonProperty("dttm")]
        public string DateTime { get; set; }
        
        [JsonProperty("signature")]
        public string Signature { get; set; }

        public void FillDateTime()
        {
            this.DateTime = System.DateTime.Now.ToString(DTTM_FORMAT);
        }
    }
}
