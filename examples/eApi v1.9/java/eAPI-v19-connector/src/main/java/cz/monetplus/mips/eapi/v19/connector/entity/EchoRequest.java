package cz.monetplus.mips.eapi.v19.connector.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor
public class EchoRequest extends SignBase {

	private static final long serialVersionUID = -3825192932302805075L;
	
	@NonNull
	private String merchantId;

	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder(); 
		add(sb, merchantId);
		add(sb, dttm);
		deleteLast(sb);
		return sb.toString();
	}

}
