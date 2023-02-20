package cz.monetplus.mips.eapi.v19.connector.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import cz.monetplus.mips.eapi.v19.connector.entity.ext.Extension;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor @NoArgsConstructor @RequiredArgsConstructor
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApplepayInitRequest extends SignBase {

    @NonNull
    private String merchantId;
    @NonNull
    private String orderNo;
    private String payMethod;
    @NonNull
    private String clientIp;
    @NonNull
    private Long totalAmount;
    @NonNull
    private String currency;
    private Boolean closePayment;
    @NonNull
    private String payload;
    @NonNull
    private String returnUrl;
    private String returnMethod; //[GET, POST]
    private Customer customer;
    private Order order;
    private Boolean sdkUsed;
    private String merchantData;

    private Integer ttlSec;
    private List<Extension> extensions;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, orderNo);
        add(sb, dttm);
        add(sb, payMethod);
        add(sb, clientIp);
        add(sb, totalAmount);
        add(sb, currency);
        add(sb, closePayment);
        add(sb, payload);
        add(sb, returnUrl);
        add(sb, returnMethod);
        if (null != customer) add(sb, customer.toSign());
        if (null != order) add(sb, order.toSign());
        add(sb, sdkUsed);
        add(sb, merchantData);
        add(sb, ttlSec);
        deleteLast(sb);
        return sb.toString();
    }
}
