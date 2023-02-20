package cz.monetplus.mips.eapi.v19.connector.entity.ext;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MaskedClnRPExtensionV16 extends Extension {

	private static final long serialVersionUID = -3825192932302805075L;
	
	private String maskedCln;

	private String longMaskedCln;

	private String expiration;


	public MaskedClnRPExtensionV16(String dttm, String maskedCln, String longMaskedCln, String expiration) {
		this.dttm = dttm;
		this.maskedCln = maskedCln;
		this.longMaskedCln = longMaskedCln;
		this.expiration = expiration;
		this.extension = PrivateApiExtension.RECURRENT_PAYMENT_MASK_CLN.getCode();
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
