package cz.monetplus.mips.eapi.v18.connector.entity;

import java.io.Serializable;

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
	
	protected void add(StringBuilder sb, boolean value) {
		sb.append(value).append(SEP);
	}

	protected void add(StringBuilder sb, Integer value) {
		if (value != null) {
			sb.append(value).append(SEP);
		}
	}

	protected void add(StringBuilder sb, int value) {
		sb.append(value).append(SEP);
	}

	protected void add(StringBuilder sb, long value) {
		sb.append(value).append(SEP);
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
