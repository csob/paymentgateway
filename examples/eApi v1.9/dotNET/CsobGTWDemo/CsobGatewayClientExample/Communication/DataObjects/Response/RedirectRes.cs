using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects.Response;

public class RedirectRes
{
    [JsonProperty("url")] public string? Url { get; set; }

    [JsonProperty("method")] public string? Method { get; set; }

    [JsonProperty("params")] public Dictionary<string, string>? Params { get; set; }
}