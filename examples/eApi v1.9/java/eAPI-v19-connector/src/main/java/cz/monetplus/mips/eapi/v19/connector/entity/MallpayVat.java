package cz.monetplus.mips.eapi.v19.connector.entity;

public class MallpayVat extends ApiBase implements Signable {
    public Long amount;
    public String currency;
    public Long vatRate;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, amount);
        add(sb, currency);
        add(sb, vatRate);
        deleteLast(sb);
        return sb.toString();
    }
}
