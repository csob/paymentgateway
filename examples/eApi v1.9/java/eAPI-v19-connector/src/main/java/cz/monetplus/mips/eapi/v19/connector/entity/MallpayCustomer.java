package cz.monetplus.mips.eapi.v19.connector.entity;

public class MallpayCustomer extends ApiBase implements Signable {
    public String firstName;
    public String lastName;
    public String fullName;
    public String titleBefore;
    public String titleAfter;
    public String email;
    public String phone;
    public String tin;
    public String vatin;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, firstName);
        add(sb, lastName);
        add(sb, fullName);
        add(sb, titleBefore);
        add(sb, titleAfter);
        add(sb, email);
        add(sb, phone);
        add(sb, tin);
        add(sb, vatin);
        deleteLast(sb);
        return sb.toString();
    }

}
