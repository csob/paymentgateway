using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public abstract class SignBaseRequest : SignBase, IBaseRequest
{
    [JsonProperty("merchantId")] public string? MerchantId { get; set; }

    public void FillDateTime()
    {
        Dttm = DateTime.Now.ToString(DTTM_FORMAT);
    }
}