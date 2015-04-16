package cz.monetplus.mips.eapi.v1.connector.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class SignBase extends ApiBase implements Signable {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@XmlElement(nillable=false)
	public String dttm;

	@XmlElement(nillable=false)
	public String signature;

	public void fillDttm() {
		this.dttm = DateTime.now().toString(DTTM_FORMAT);
	}

}
