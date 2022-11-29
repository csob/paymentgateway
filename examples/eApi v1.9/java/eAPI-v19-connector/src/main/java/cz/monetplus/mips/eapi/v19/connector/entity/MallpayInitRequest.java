package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor @RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MallpayInitRequest  extends SignBase {
    @NonNull
    private String merchantId;
    @NonNull
    private String orderNo;
    @NonNull
    private MallpayCustomer customer;
    @NonNull
    private MallpayOrder order;
    @NonNull
    private Boolean agreeTC;
    @NonNull
    private String clientIp;
    @NonNull
    private String returnUrl;
    @NonNull
    private String returnMethod;
    private String merchantData;
    private Long ttlSec;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, orderNo);
        add(sb, customer.toSign());
        add(sb, order.toSign());
        add(sb, agreeTC);
        add(sb, dttm);
        add(sb, clientIp);
        add(sb, returnUrl);
        add(sb, returnMethod);
        add(sb, merchantData);
        add(sb, ttlSec);
        deleteLast(sb);
        return sb.toString();
    }
}
