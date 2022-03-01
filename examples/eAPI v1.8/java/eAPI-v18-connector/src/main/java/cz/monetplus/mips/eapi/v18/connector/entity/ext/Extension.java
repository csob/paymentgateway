package cz.monetplus.mips.eapi.v18.connector.entity.ext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import cz.monetplus.mips.eapi.v18.connector.entity.SignBase;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "extension", visible=true)
@JsonSubTypes({ 
	@JsonSubTypes.Type(value = TrxDatesExtension.class, name = "trxDates"), 
	@JsonSubTypes.Type(value = MaskedClnRPExtensionV16.class, name = "maskClnRP")
})
public abstract class Extension extends SignBase {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@XmlElement
	public String extension;

	public Extension() {
		
	}

}
