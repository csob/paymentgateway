package cz.monetplus.mips.eapi.v19.connector.entity.ext;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "extension", visible=true)
@Data
@EqualsAndHashCode(callSuper = false)
@JsonSubTypes({ 
	@JsonSubTypes.Type(value = TrxDatesExtension.class, name = "trxDates"), 
	@JsonSubTypes.Type(value = MaskedClnRPExtensionV16.class, name = "maskClnRP")
})
public abstract class Extension extends SignBase {

	private static final long serialVersionUID = -3825192932302805075L;
	
	protected String extension;



}
