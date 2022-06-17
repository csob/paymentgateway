package cz.monetplus.mips.eapi.v19.connector.entity;

class Address extends ApiBase implements Signable {
    public String address1;
    public String address2;
    public String address3;
    public String city;
    public String zip;
    public String state;
    public String country;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, address1);
        add(sb, address2);
        add(sb, address3);
        add(sb, city);
        add(sb, zip);
        add(sb, state);
        add(sb, country);
        deleteLast(sb);
        return sb.toString();
    }
}
