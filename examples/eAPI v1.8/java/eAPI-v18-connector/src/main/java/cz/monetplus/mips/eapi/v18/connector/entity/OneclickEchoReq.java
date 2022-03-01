package cz.monetplus.mips.eapi.v18.connector.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OneclickEchoReq extends SignBase {

	private static final long serialVersionUID = -438284924457338635L;

	@XmlElement(nillable=false)
	public String merchantId;
	
	@XmlElement(nillable=false)
	public String origPayId;
	
	public OneclickEchoReq() {
		
	}

	public OneclickEchoReq(String merchantId, String origPayId) {
		this.merchantId = merchantId;
		this.origPayId = origPayId;
	}

	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, merchantId);
		add(sb, origPayId);
		add(sb, dttm);
		deleteLast(sb);
		return sb.toString();
	}

}
