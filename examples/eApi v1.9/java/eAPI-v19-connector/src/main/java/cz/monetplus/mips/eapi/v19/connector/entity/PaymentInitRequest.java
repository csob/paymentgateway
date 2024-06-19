package cz.monetplus.mips.eapi.v19.connector.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import cz.monetplus.mips.eapi.v19.connector.entity.ext.Extension;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PaymentInitRequest extends SignBase {
    @NonNull
    private String merchantId;
    @NonNull
    private String orderNo;
    private String payOperation; //[payment, oneclickPayment, customPayment]
    private String payMethod; //[card, card#LVP]
    @NonNull
    private Long totalAmount;
    @NonNull
    private String currency;
    private Boolean closePayment = true;
    @NonNull
    private List<CartItem> cart;
    @NonNull
    private String returnUrl;
    @NonNull
    private String returnMethod; //[GET, POST]
    private Customer customer;
    private Order order;
    private String merchantData;
    private String customerId;
    @NonNull
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