package cz.monetplus.mips.eapi.v1.connector.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PayInitReq extends SignBase {

	private static final long serialVersionUID = -438284924457338635L;

	@XmlElement(nillable=false)
	public String merchantId;
	
	@XmlElement(nillable=false)
	public String orderNo;

	@XmlElement(nillable=false)
	public String payOperation;
	
	@XmlElement(nillable=false)
	public String payMethod;

	@XmlElement(nillable=false)
	public long totalAmount;

	@XmlElement(nillable=false)
	public String currency;

	@XmlElement(nillable=false)
	public boolean closePayment;
	
	@XmlElement(nillable=false)
	public String returnUrl;

	@XmlElement(nillable=false)
	public String returnMethod;

	@XmlElement(nillable=false)
	public List<CartItem> cart;
	
	@XmlElement(nillable=false)
	public String description;

	@XmlElement(nillable=true)
	public String merchantData;

	@XmlElement(nillable=true)
	public String customerId;
	
	@XmlElement(nillable=true)
	public String language;
	
	@Override
	public String toSign() {
		StringBuffer sb = new StringBuffer();
		add(sb, merchantId);
		add(sb, orderNo);
		add(sb, dttm);
		add(sb, payOperation);
		add(sb, payMethod);
		add(sb, totalAmount);
		add(sb, currency);
		add(sb, closePayment);
		add(sb, returnUrl);
		add(sb, returnMethod);
		for (CartItem item : cart) {
			add(sb, item.toSign());
		}
		add(sb, description);
		add(sb, merchantData);
		add(sb, customerId);
		add(sb, language);
		deleteLast(sb);
		return sb.toString();
	}

}
