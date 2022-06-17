package cz.monetplus.mips.eapi.v19.connector.entity;

public class MallpayOrderItemRef extends ApiBase  implements Signable {
    public String code;
    public String ean;
    public String name;
    public String type;
    public Long quantity;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, code);
        add(sb, ean);
        add(sb, name);
        add(sb, type);
        add(sb, quantity);
        deleteLast(sb);
        return sb.toString();
    }
}
