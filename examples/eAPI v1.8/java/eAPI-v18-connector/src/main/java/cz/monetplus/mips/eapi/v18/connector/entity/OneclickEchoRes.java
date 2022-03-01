package cz.monetplus.mips.eapi.v18.connector.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OneclickEchoRes extends SignBase {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@XmlElement
	public String origPayId;

	@XmlElement
	public int resultCode;

	@XmlElement
	public String resultMessage;

	public OneclickEchoRes() {
		
	}

	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, origPayId);
		add(sb, dttm);
		add(sb, resultCode);
		add(sb, resultMessage);
		deleteLast(sb);
		return sb.toString();
	}

}
