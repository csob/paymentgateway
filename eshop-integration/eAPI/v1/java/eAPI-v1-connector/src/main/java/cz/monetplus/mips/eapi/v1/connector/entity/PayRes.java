package cz.monetplus.mips.eapi.v1.connector.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PayRes extends SignBase {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@XmlElement
	public String payId;

	@XmlElement
	public int resultCode;

	@XmlElement
	public String resultMessage;

	@XmlElement
	public Integer paymentStatus;

	@XmlElement
	public String authCode;

	public PayRes() {
		
	}

	@Override
	public String toSign() {
		StringBuffer sb = new StringBuffer();
		add(sb, payId);
		add(sb, dttm);
		add(sb, resultCode);
		add(sb, resultMessage);
		add(sb, paymentStatus);
		add(sb, authCode);
		deleteLast(sb);
		return sb.toString();
	}

}
