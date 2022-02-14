package cz.monetplus.mips.eapi.v18.service;


public class MipsException extends Exception {

	private static final long serialVersionUID = 7127060973077780861L;

	private RespCode respCode = RespCode.INTERNAL_ERROR;  
	
	public MipsException(RespCode respCode, String message) {
		super(message);
		this.respCode = respCode;
	}

	public MipsException(RespCode respCode, String message, Throwable cause) {
		super(message, cause);
		this.respCode = respCode;
	}

	public RespCode getRespCode()
	{
		return respCode;
	}
	
	public String getLongMessage()
	{
		return respCode + "[" + respCode.getCode() + "] :: " + getMessage();
	}
	
}
