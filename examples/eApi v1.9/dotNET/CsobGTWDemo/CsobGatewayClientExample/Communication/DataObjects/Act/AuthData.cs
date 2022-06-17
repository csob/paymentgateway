using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects.Act;

public class AuthData : ApiBase
{
    [JsonProperty("browser")] public Auth3dsBrowser? Browser { get; set; }

    [JsonProperty("sdk")] public Auth3dsSdk? Sdk { get; set; }


    public string? ToSign()
    {
        var sb = new StringBuilder();
        if (null != Browser) sb.Append(Browser.ToSign());
        if (null != Sdk) sb.Append(Sdk.ToSign());
        DeleteLast(sb);
        return sb.ToString();
    }
}