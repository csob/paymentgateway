package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor @NoArgsConstructor
public class MallpayInitRequest  extends SignBase {
    private String merchantId;
    private String orderNo;
    private MallpayCustomer customer;
    private MallpayOrder order;
    private Boolean agreeTC;
    private String clientIp;
    private String returnUrl;
    private String returnMethod;
    private String merchantData;
    private Long ttlSec;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, orderNo);
        if (null != customer) add(sb, customer.toSign());
        if (null != order) add(sb, order.toSign());
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
