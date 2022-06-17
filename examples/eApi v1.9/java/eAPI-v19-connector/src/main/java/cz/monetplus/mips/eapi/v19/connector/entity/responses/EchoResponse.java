package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;

public class EchoResponse extends SignBase {

	private static final long serialVersionUID = -3825192932302805075L;
	
	public int resultCode;
	public String resultMessage;

	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, dttm);
		add(sb, resultCode);
		add(sb, resultMessage);
		deleteLast(sb);
		return sb.toString();
	}

}
