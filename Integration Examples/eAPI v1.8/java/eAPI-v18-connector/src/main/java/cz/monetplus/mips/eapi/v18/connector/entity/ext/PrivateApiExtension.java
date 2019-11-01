package cz.monetplus.mips.eapi.v18.connector.entity.ext;

public enum PrivateApiExtension {

	RECURRENT_PAYMENT_MASK_CLN("maskClnRP"),

	TRX_DATES("trxDates"),

	;
	
	private final String code;
	
	private PrivateApiExtension(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	
}
