package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor @RequiredArgsConstructor
public class LoanCancelRequest  extends SignBase {
    @NonNull
    private String merchantId;
    @NonNull
    private String payId;
    @NonNull
    private String reason;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, payId);
        add(sb, reason);
        add(sb, dttm);
        deleteLast(sb);
        return sb.toString();
    }
}
