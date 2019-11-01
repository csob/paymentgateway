package cz.monetplus.mips.eapi.v18.service;

public enum RespCode {

	OK(0, "OK"),
	INTERNAL_ERROR(900, "Internal error"),
	MISSING_PARAM(100, "Missing parameter"),
	INVALID_PARAM(110, "Invalid parameter"),
	MERCHANT_BLOCKED(120, "Merchant blocked"),
	SESSION_EXPIRED(130, "Session expired"),
	PAYMENT_NOT_FOUND(140, "Payment not found"),
	PAYMENT_INVALID_STATE(150, "Payment not in valid state"),
	PAYMENT_METHOD_DISABLED(160, "Payment method disabled"),
	PAYMENT_METHOD_UNAVAILABLE(170, "Payment method unavailable"),
	OPERATION_NOT_ALLOWED(180, "Operation not allowed"),
	PAYMENT_METHOD_PROCESSING_FAILED(190, "Payment method processing failed"),
	
	MPASS_MERCHANT_NOT_ONBOARDED(230, "Merchant not onboarded for MasterPass"),
	MPASS_SESSION_ALREADY_INITIALIZED(240, "MasterPass request token already initialized"),
	MPASS_SESSION_DOES_NOT_EXIST(250, "MasterPass request token does not exist"),
	MPASS_CANCELED_BY_USER(270, "MasterPass canceled by user"),
	
	EET_REJECTED(500, "Rejected by EET"),

	MALLPAY_PAYMENT_DECLINED_IN_PRECHECK(600, "MALL Pay payment declined in precheck"),

	ONECLICK_TEMPLATE_NOT_FOUND(700, "Oneclick template not found"),
	ONECLICK_TEMPLATE_PAYMENT_EXPIRED(710, "Oneclick template payment expired"),
	ONECLICK_TEMPLATE_CARD_EXPIRED(720, "Oneclick template card expired"),
	ONECLICK_TEMPLATE_CUSTOMER_REJECTED(730, "Oneclick template customer rejected"),
	ONECLICK_TEMPLATE_PAYMENT_REVERSED(740, "Oneclick template payment reversed"),

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
