package cz.monetplus.mips.eapi.v18.connector.entity.ext;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MaskedClnRPExtensionV16 extends Extension {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@XmlElement
	public String extension = PrivateApiExtension.RECURRENT_PAYMENT_MASK_CLN.getCode();

	@XmlElement
	public String maskedCln;

	@XmlElement
	public String longMaskedCln;

	@XmlElement
	public String expiration;

	public MaskedClnRPExtensionV16() {
	}

	public MaskedClnRPExtensionV16(String dttm, String maskedCln, String longMaskedCln, String expiration) {
		this.dttm = dttm;
		this.maskedCln = maskedCln;
		this.longMaskedCln = longMaskedCln;
		this.expiration = expiration;
	}

	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, extension);
		add(sb, dttm);
		add(sb, maskedCln);
		add(sb, expiration);
		add(sb, longMaskedCln);
		deleteLast(sb);
		return sb.toString();
	}

}
