package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class PaymentCloseRequest  extends SignBase {
    @NonNull
    private  String merchantId;
    @NonNull
    public String payId;
    public Long totalAmount;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, payId);
        add(sb, dttm);
        add(sb, totalAmount);
        deleteLast(sb);
        return sb.toString();
    }
}
