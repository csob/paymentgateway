package cz.monetplus.mips.eapi.v1.connector.entity;

import java.io.Serializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class ApiBase implements Serializable {

	private static final long serialVersionUID = -3825192932302805075L;
	
	public static final char SEP = '|'; 
	
	public static final int MERCHANT_ID_MAX_LEN = 10;
	public static final int ORDER_NO_MAX_LEN = 10;
	public static final int PAY_ID_MAX_LEN = 15;
	public static final int RETURNURL_MAX_LEN = 300;
	public static final int CART_ITEM_NAME_MAX_LEN = 20;
	public static final int CART_ITEM_DESC_MAX_LEN = 40;
	public static final int DESCRIPTION_MAX_LEN = 255;
	public static final int CUST_ID_MAX_LEN = 50;
	public static final int MERCHANTDATA_MAX_LEN = 255;
	public static final int SIGNATURE_MAX_LEN = 500;
	public static final int CARDNAME_MAX_LEN = 20;
	
	public static final String[] PAY_OPER_ALLOWED_VALUES = new String[] { "payment" };
	public static final String[] PAY_METHOD_ALLOWED_VALUES = new String[] { "card" };
	public static final String[] RETURN_METHOD_ALLOWED_VALUES = new String[] { "POST", "GET" };
	public static final String[] CURRENCY_ALLOWED_VALUES = new String[] { "CZK", "EUR", "USD", "GBP" };
	public static final String[] LANGUAGE_ALLOWED_VALUES = new String[] { "CZ", "EN", "DE", "SK" };
	
	public static final String DTTM_FORMAT = "YYYYMMddHHmmss";
	
	protected void add(StringBuffer sb, String value) {
		if (value != null) {
			sb.append(value).append(SEP);
		}
	}
	
	protected void add(StringBuffer sb, boolean value) {
		sb.append(value).append(SEP);
	}

	protected void add(StringBuffer sb, Integer value) {
		if (value != null) {
			sb.append(value).append(SEP);
		}
	}

	protected void add(StringBuffer sb, int value) {
		sb.append(value).append(SEP);
	}

	protected void add(StringBuffer sb, long value) {
		sb.append(value).append(SEP);
	}
	
	protected void deleteLast(StringBuffer sb) {
		if (SEP == sb.charAt(sb.length()-1)) {
			sb.deleteCharAt(sb.length()-1);
		}
	}

	public String toJson() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}
	
}
