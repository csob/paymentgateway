package cz.monetplus.mips.eapi.v19.connector.entity;

public class MallpayAddress extends ApiBase implements Signable {
    public String name;
    public String country;
    public String city;
    public String streetAddress;
    public String streetNumber;
    public String zip;
    public String addressType;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, name);
        add(sb, country);
        add(sb, city);
        add(sb, streetAddress);
        add(sb, streetNumber);
        add(sb, zip);
        add(sb, addressType);
        deleteLast(sb);
        return sb.toString();
    }
}
