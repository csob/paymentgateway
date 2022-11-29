package cz.monetplus.mips.eapi.v19.connector.entity.ext;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TrxDatesExtension extends Extension {

	private static final long serialVersionUID = -3825192932302805075L;
	
	private String createdDate;
	
	private String authDate;

	private String settlementDate;

	public TrxDatesExtension() {
		this.extension = PrivateApiExtension.TRX_DATES.getCode();
	}
	
	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, getExtension());
		add(sb, dttm);
		add(sb, createdDate);
		add(sb, authDate);
		add(sb, settlementDate);
		deleteLast(sb);
		return sb.toString();
	}

}
