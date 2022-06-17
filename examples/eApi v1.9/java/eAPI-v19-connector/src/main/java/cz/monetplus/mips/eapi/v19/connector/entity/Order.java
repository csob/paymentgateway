package cz.monetplus.mips.eapi.v19.connector.entity;

class Order extends ApiBase implements Signable {
    public String type; //[purchase, balance, prepaid, cash, check]
    public String availability;
    public String delivery; //[shipping, shipping_verified, instore, digital, ticket, order]
    public String deliveryMode; //[0, 1, 2, 3]
    public String deliveryEmail;
    public boolean nameMatch;
    public boolean addressMatch;
    public Address billing;
    public Address shipping;
    public String shippingAddedAt;
    public boolean reorder;
    public GiftCards giftcards;

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

    public static class GiftCards extends ApiBase implements Signable {
        public long totalAmount;
        public String currency;
        public int quantity;

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
