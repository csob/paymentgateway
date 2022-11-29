package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor @AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PaymentRefundRequest  extends SignBase {
    @NonNull
    private String merchantId;
    @NonNull
    private String payId;
    private Long amount;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, payId);
        add(sb, dttm);
        add(sb, amount);
        deleteLast(sb);
        return sb.toString();
    }
}
