package cz.monetplus.mips.eapi.v1.service;

public enum RespCode {

	OK(0, "OK"),
	
	UNKNOWN(999),

	INTERNAL_ERROR(900, "Internal error"),

	MISSING_PARAM(100, "Missing parameter"),
	
	INVALID_PARAM(110, "Invalid parameter"),

	MERCHANT_BLOCKED(120, "Merchant blocked"),

	SESSION_EXPIRED(130, "Session expired"),

	PAYMENT_NOT_FOUND(140, "Payment not found"),

	PAYMENT_INVALID_STATE(150, "Payment not in valid state"),

	CUSTOMER_NOT_FOUND(800, "Customer not found"),

	CUSTOMER_FOUND_NO_CARDS(810, "Customer found, no saved card(s)"),

	CUSTOMER_FOUND_WITH_CARDS(820, "Customer found, found saved card(s)"),

	;
	
	private final int code;
	
	private final String msg;

	public static final String EMPTY = "";
	
	private RespCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	private RespCode(int code) {
		this(code, EMPTY);
	}

	public int getCode()
	{
		return code;
	}
	
	public String getMsg()
	{
		return msg;
	}
	
	public boolean hasEmptyMsg()
	{
		return EMPTY.equals(msg);
	}

}
