package cz.monetplus.mips.eapi.v19.connector.entity;


import lombok.Setter;
import org.joda.time.DateTime;

import lombok.Getter;

public abstract class SignBase extends ApiBase implements Signable {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@Getter @Setter
	protected String dttm;

	@Getter @Setter
	protected String signature;

	public void fillDttm() {
		this.dttm = DateTime.now().toString(DTTM_FORMAT);
	}

}
