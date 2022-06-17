using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class GooglepayInitParams : SignBase
{
    private int ApiVersion { get; } = 2;

    private int ApiVersionMinor { get; } = 0;

    public string? PaymentMethodType { get; set; }

    // ReSharper disable once CollectionNeverUpdated.Global
    private List<string>? AllowedCardNetworks { get; set; }

    // ReSharper disable once CollectionNeverUpdated.Global
    private List<string>? AllowedCardAuthMethods { get; set; }


    public bool? AssuranceDetailsRequired { get; set; }

    public bool? BillingAddressRequired { get; set; }

    public string? BillingAddressParametersFormat { get; set; }

    public string? TokenizationSpecificationType { get; set; }

    public string? Gateway { get; set; }

    public string? GatewayMerchantId { get; set; }

    public string? GooglepayMerchantId { get; set; }

    public string? MerchantName { get; set; }

    public string? Environment { get; set; }

    public string? TotalPriceStatus { get; set; }

    public string? CountryCode { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, ApiVersion);
        Add(sb, ApiVersionMinor);
        if (AllowedCardNetworks != null)
            foreach (var value in AllowedCardNetworks)
                Add(sb, value);
        if (AllowedCardAuthMethods != null)
            foreach (var value in AllowedCardAuthMethods)
                Add(sb, value);
        Add(sb, GooglepayMerchantId);
        Add(sb, MerchantName);
        Add(sb, TotalPriceStatus);
        DeleteLast(sb);
        return sb.ToString();
    }
}