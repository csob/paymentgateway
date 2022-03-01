package cz.monetplus.mips.eapi.v18.connector.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cz.monetplus.mips.eapi.v18.connector.entity.ext.Extension;

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

	@XmlElement
	public String customerCode;

	@XmlElement
	public List<Extension> extensions;

	public PayRes() {
		
	}

	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, payId);
		add(sb, dttm);
		add(sb, resultCode);
		add(sb, resultMessage);
		add(sb, paymentStatus);
		add(sb, authCode);
		add(sb, customerCode);
		deleteLast(sb);
		return sb.toString();
	}

}
