package cz.monetplus.mips.eapi.v1.connector.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EchoReq extends SignBase {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@XmlElement(nillable=false)
	public String merchantId;
	
	public EchoReq() {
	}
	
	public EchoReq(String merchantId) {
		this.merchantId = merchantId;
	}

	@Override
	public String toSign() {
		StringBuffer sb = new StringBuffer(); 
		add(sb, merchantId);
		add(sb, dttm);
		deleteLast(sb);
		return sb.toString();
	}

}
