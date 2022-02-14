package cz.monetplus.mips.eapi.v18.connector.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CustReq extends SignBase {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@XmlElement(nillable=false)
	public String merchantId;

	@XmlElement(nillable=false)
	public String customerId;

	public CustReq() {
		
	}

	public CustReq(String merchantId, String customerId) {
		this.merchantId = merchantId;
		this.customerId = customerId;
	}

	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, merchantId);
		add(sb, customerId);
		add(sb, dttm);
		deleteLast(sb);
		return sb.toString();
	}


}
