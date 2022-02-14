package cz.monetplus.mips.eapi.v18.connector.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PayCloseReq extends PayReq {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@XmlElement(nillable=true)
	public Long totalAmount;

	public PayCloseReq() {
		
	}

	public PayCloseReq(String merchantId, String payId, Long totalAmount) {
		super(merchantId, payId);
		this.totalAmount = totalAmount;
	}

	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, merchantId);
		add(sb, payId);
		add(sb, dttm);
		add(sb, totalAmount);
		deleteLast(sb);
		return sb.toString();
	}

}
