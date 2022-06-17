package cz.monetplus.mips.eapi.v19.connector.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class ApiBase implements Serializable {

	private static final long serialVersionUID = -3825192932302805075L;
	
	public static final char SEP = '|'; 
	
	public static final String DTTM_FORMAT = "YYYYMMddHHmmss";
	
	protected void add(StringBuilder sb, String value) {
		if (value != null) {
			sb.append(value).append(SEP);
		}
	}
	
	protected void add(StringBuilder sb, Boolean value) {
		if (value != null) {
			sb.append(value).append(SEP);
		}
	}

	protected void add(StringBuilder sb, Integer value) {
		if (value != null) {
			sb.append(value).append(SEP);
		}
	}

	protected void add(StringBuilder sb, int value) {
		sb.append(value).append(SEP);
	}

	protected void add(StringBuilder sb, Long value) {
		if (value != null) {
			sb.append(value).append(SEP);
		}
	}

	protected void add(StringBuilder sb, BigDecimal value) {
		if (value != null) {
			sb.append(value.setScale(2)).append(SEP);
		}
	}
	
	protected void deleteLast(StringBuilder sb) {
		if (SEP == sb.charAt(sb.length()-1)) {
			sb.deleteCharAt(sb.length()-1);
		}
	}

	public String toJson() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	
}
