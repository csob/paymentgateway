using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects.Response;

public class ApplepayInitParams : SignBase
{
    public string? CountryCode;
    public List<string>? SupportedNetworks { get; set; }
    public List<string>? MerchantCapabilities { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, CountryCode);
        if (SupportedNetworks != null)
            foreach (var value in SupportedNetworks)
                Add(sb, value);
        if (MerchantCapabilities != null)
            foreach (var value in MerchantCapabilities)
                Add(sb, value);
        DeleteLast(sb);
        return sb.ToString();
    }
}