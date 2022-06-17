package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor @NoArgsConstructor
public class LoanInitResponse  extends SignBase {
    private String payId;
    private Long resultCode;
    private String resultMessage;
    private Long paymentStatus;
    private String comparisonUrl;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, payId);
        add(sb, dttm);
        add(sb, resultCode);
        add(sb, resultMessage);
        add(sb, paymentStatus);
        add(sb, comparisonUrl);
        deleteLast(sb);
        return sb.toString();
    }
}
