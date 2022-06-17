package cz.monetplus.mips.eapi.v19.connector.entity;

import java.util.List;

import cz.monetplus.mips.eapi.v19.connector.entity.ext.Extension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor 
public class GooglepayInitRequest extends SignBase {

    private static final long serialVersionUID = -438284924457338635L;
    
    private String merchantId;
    private String orderNo;
    private String payMethod;
    private String clientIp;
    private Long totalAmount;
    private String currency;
    private boolean closePayment;
    private String payload;
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
