package cz.monetplus.mips.eapi.v19.connector.entity;

public class LoanCustomer extends ApiBase implements Signable {
    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public LoanAddress billingAddress;
    public LoanAddress shippingAddress;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, firstName);
        add(sb, lastName);
        add(sb, email);
        add(sb, phone);
        if(null != billingAddress) add(sb, billingAddress.toSign());
        if(null != shippingAddress) add(sb, shippingAddress.toSign());
        deleteLast(sb);
        return sb.toString();
    }
}
