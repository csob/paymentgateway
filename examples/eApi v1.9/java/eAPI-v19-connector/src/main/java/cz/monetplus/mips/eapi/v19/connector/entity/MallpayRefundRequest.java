package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor @RequiredArgsConstructor
public class MallpayRefundRequest extends SignBase {
    @NonNull
    private String merchantId;
    @NonNull
    private String payId;
    @NonNull
    private Long amount;
    @NonNull
    private List<MallpayOrderItemRef> refundedItems;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, payId);
        add(sb, dttm);
        add(sb, amount);
        if(refundedItems != null) for(MallpayOrderItemRef item: refundedItems) add(sb, item.toSign());
        deleteLast(sb);
        return sb.toString();
    }
}
