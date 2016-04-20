package cz.monetplus.mips.eapi.v16.connector.entity.ext;

public enum PrivateApiExtension {

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
