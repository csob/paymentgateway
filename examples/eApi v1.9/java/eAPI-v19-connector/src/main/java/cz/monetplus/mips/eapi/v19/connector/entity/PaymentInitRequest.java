package cz.monetplus.mips.eapi.v19.connector.entity;

import java.util.List;

import cz.monetplus.mips.eapi.v19.connector.entity.ext.Extension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitRequest extends SignBase {
    private String merchantId;
    private String orderNo;
    private String payOperation; //[payment, oneclickPayment, customPayment]
    private String payMethod; //[card, cart#LVP]
    private Long totalAmount;
    private String currency;
    private Boolean closePayment = true;
    private List<CartItem> cart;
    private String returnUrl;
    private String returnMethod; //[GET, POST]
    private Customer customer;
    private Order order;
    private String merchantData;
    private String customerId;
    private String language;
    private Integer ttlSec;
    private Integer logoVersion;
    private Integer colorSchemeVersion;
    private String customExpiry;
    private List<Extension> extensions;

    
    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, orderNo);
        add(sb, dttm);
        add(sb, payOperation);
        add(sb, payMethod);
        add(sb, totalAmount);
        add(sb, currency);
        add(sb, closePayment);
        add(sb, returnUrl);
        add(sb, returnMethod);

        for (CartItem item : cart) {
            add(sb, item.toSign());
        }

        if (null != customer) add(sb, customer.toSign());
        if (null != order) add(sb, order.toSign());
        add(sb, merchantData);
        add(sb, customerId);
        add(sb, language);
        add(sb, ttlSec);
        add(sb, logoVersion);
        add(sb, colorSchemeVersion);
        add(sb, customExpiry);
        deleteLast(sb);
        return sb.toString();
    }
}