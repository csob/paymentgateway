using System.Text;
using System.Xml.Serialization;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects.Ext;

[XmlRoot("maskClnRP")]
public class MaskedClnRPExtensionV16 : Extension
{
    [XmlElement(ElementName = "extension")] [JsonProperty("extension")]
    public string Extension = PrivateApiExtension.RECURRENT_PAYMENT_MASK_CLN;

    public MaskedClnRPExtensionV16()
    {
    }

    public MaskedClnRPExtensionV16(string dttm, string? maskedCln, string? longMaskedCln, string? expiration)
    {
        Dttm = Dttm;
        MaskedCln = maskedCln;
        LongMaskedCln = longMaskedCln;
        Expiration = expiration;
    }

    //@XmlElement
    [XmlElement(ElementName = "maskedCln")]
    [JsonProperty("maskedCln")]
    public string? MaskedCln { get; set; }

    //@XmlElement
    [XmlElement(ElementName = "longMaskedCln")]
    [JsonProperty("longMaskedCln")]
    public string? LongMaskedCln { get; set; }

    //@XmlElement
    [XmlElement(ElementName = "expiration")]
    [JsonProperty("expiration")]
    public string? Expiration { get; set; }

    //@Override
    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, extension);
        Add(sb, Dttm);
        Add(sb, MaskedCln);
        Add(sb, Expiration);
        Add(sb, LongMaskedCln);
        DeleteLast(sb);
        return sb.ToString();
    }
}