package cz.monetplus.mips.eapi.v19.connector.entity;

public class MallpayPrice  extends ApiBase implements Signable {
    public Long amount;
    public String currency;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, amount);
        add(sb, currency);
        deleteLast(sb);
        return sb.toString();
    }
}