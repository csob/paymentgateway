package cz.monetplus.mips.eapi.v18.connector.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PayReq extends SignBase {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@XmlElement(nillable=false)
	public String merchantId;

	@XmlElement(nillable=false)
	public String payId;

	public PayReq() {
		
	}

	public PayReq(String merchantId, String payId) {
		this.merchantId = merchantId;
		this.payId = payId;
	}

	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, merchantId);
		add(sb, payId);
		add(sb, dttm);
		deleteLast(sb);
		return sb.toString();
	}

}
