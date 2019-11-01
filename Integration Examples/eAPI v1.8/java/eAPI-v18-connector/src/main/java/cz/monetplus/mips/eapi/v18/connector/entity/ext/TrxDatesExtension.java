package cz.monetplus.mips.eapi.v18.connector.entity.ext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TrxDatesExtension extends Extension {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@XmlElement
	public String createdDate;
	
	@XmlElement
	public String authDate;

	@XmlElement
	public String settlementDate;
	
	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, extension);
		add(sb, dttm);
		add(sb, createdDate);
		add(sb, authDate);
		add(sb, settlementDate);
		deleteLast(sb);
		return sb.toString();
	}

}
