package cz.monetplus.mips.eapi.v18.connector.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OneclickInitReq extends SignBase {

	private static final long serialVersionUID = -438284924457338635L;

	@XmlElement(nillable=false)
	public String merchantId;
	
	@XmlElement(nillable=false)
	public String origPayId;

	@XmlElement(nillable=false)
	public String orderNo;

	@XmlElement(nillable=false)
	public String clientIp;

	@XmlElement(nillable=true)
	public Long totalAmount;

	@XmlElement(nillable=true)
	public String currency;

	@XmlElement(nillable=true)
	public String merchantData;

	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, merchantId);
		add(sb, origPayId);
		add(sb, orderNo);
		add(sb, dttm);
		add(sb, clientIp);
		add(sb, totalAmount);
		add(sb, currency);
		add(sb, merchantData);
		deleteLast(sb);
		return sb.toString();
	}

}
