package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor @RequiredArgsConstructor
public class LoanRefundRequest  extends SignBase {
    @NonNull
    private String merchantId;
    @NonNull
    private String payId;
    @NonNull
    private String reason;
    @NonNull
    private String date;
    @NonNull
    private Long amount;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, payId);
        add(sb, dttm);
        add(sb, amount);
        add(sb, reason);
        add(sb, date);
        deleteLast(sb);
        return sb.toString();
    }
}
