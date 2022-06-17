using System.Xml.Serialization;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects.Ext;
/*@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "extension", visible=true)
@JsonSubTypes({ 
	@JsonSubTypes.Type(value = TrxDatesExtension.class, name = "trxDates"), 
	@JsonSubTypes.Type(value = MaskedClnRPExtensionV16.class, name = "maskClnRP")
})*/

[XmlInclude(typeof(TrxDatesExtension))]
[XmlInclude(typeof(MaskedClnRPExtensionV16))]
public abstract class Extension : SignBase
{
    [XmlElement(ElementName = "extension")]
    [JsonProperty("extension")]
    public string? extension { get; set; }
}