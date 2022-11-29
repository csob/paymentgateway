package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CartItem extends ApiBase implements Signable {

	private static final long serialVersionUID = -3825192932302805075L;

	private String name;

	private int quantity;

	private long amount;

	private String description;


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
		StringBuilder sb = new StringBuilder(); 
		add(sb, name);
		add(sb, quantity);
		add(sb, amount);
		add(sb, description);
		deleteLast(sb);
		return sb.toString();
	}

}
