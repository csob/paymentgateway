using System.Text;
using System.Xml.Serialization;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects.Ext;

//[XmlType("NewTypeName")]
//@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
[XmlRoot("trxDates")]
public class TrxDatesExtension : Extension
{
    //private static final long serialVersionUID = -3825192932302805075L;

    //@XmlElement
    [XmlElement(ElementName = "createdDate")]
    [JsonProperty("createdDate")]
    public string? CreatedDate { get; set; }

    //@XmlElement
    [XmlElement(ElementName = "authDate")]
    [JsonProperty("authDate")]

    public string? AuthDate { get; set; }

    //@XmlElement
    [XmlElement(ElementName = "settlementDate")]
    [JsonProperty("settlementDate")]

    public string? SettlementDate { get; set; }

    //@Override
    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, extension);
        Add(sb, Dttm);
        Add(sb, CreatedDate);
        Add(sb, AuthDate);
        Add(sb, SettlementDate);
        DeleteLast(sb);
        return sb.ToString();
    }
}