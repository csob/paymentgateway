package cz.monetplus.mips.eapi.v1.connector.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CartItem extends ApiBase implements Signable {

	private static final long serialVersionUID = -3825192932302805075L;

	@XmlElement(nillable=false)
	public String name;

	@XmlElement(nillable=false)
	public int quantity;

	@XmlElement(nillable=false)
	public long amount;

	@XmlElement(nillable=true)
	public String description;

	public CartItem() {
	}

	public CartItem(String name, int quantity, long amount) {
		this.name = name;
		this.quantity = quantity;
		this.amount = amount;
	}

	public CartItem(String name, int quantity, long amount, String description) {
		this(name, quantity, amount);
		this.description = description;
	}

	@Override
	public String toSign() {
		StringBuffer sb = new StringBuffer(); 
		add(sb, name);
		add(sb, quantity);
		add(sb, amount);
		add(sb, description);
		deleteLast(sb);
		return sb.toString();
	}

}
