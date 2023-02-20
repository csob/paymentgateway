package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor @NoArgsConstructor
class Order extends ApiBase implements Signable {
    private String type; //[purchase, balance, prepaid, cash, check]
    private String availability;
    private String delivery; //[shipping, shipping_verified, instore, digital, ticket, order]
    private String deliveryMode; //[0, 1, 2, 3]
    private String deliveryEmail;
    private boolean nameMatch;
    private boolean addressMatch;
    private Address billing;
    private Address shipping;
    private String shippingAddedAt;
    private boolean reorder;
    private GiftCards giftcards;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, type);
        add(sb, availability);
        add(sb, delivery);
        add(sb, deliveryMode);
        add(sb, deliveryEmail);
        add(sb, nameMatch);
        add(sb, addressMatch);
        if (billing != null) add(sb, billing.toSign());
        if (shipping != null) add(sb, shipping.toSign());
        add(sb, shippingAddedAt);
        add(sb, reorder);
        if (giftcards != null) add(sb, giftcards.toSign());
        deleteLast(sb);
        return sb.toString();
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class GiftCards extends ApiBase implements Signable {
        private long totalAmount;
        private String currency;
        private int quantity;

        @Override
        public String toSign() {
            StringBuilder sb = new StringBuilder();
            add(sb, totalAmount);
            add(sb, currency);
            add(sb, quantity);
            deleteLast(sb);
            return sb.toString();
        }
    }
}
