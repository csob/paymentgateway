package cz.monetplus.mips.eapi.v19.connector.entity;

import java.util.List;

public class MallpayOrderItem extends ApiBase implements Signable {
    public String code;
    public String ean;
    public String name;
    public String type;
    public Long quantity;
    public String variant;
    public String description;
    public String producer;
    public List<String> categories;
    public MallpayPrice unitPrice;
    public MallpayVat unitVat;
    public MallpayPrice totalPrice;
    public MallpayVat totalVat;
    public String productUrl;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, code);
        add(sb, ean);
        add(sb, name);
        add(sb, type);
        add(sb, quantity);
        add(sb, variant);
        add(sb, description);
        add(sb, producer);
        if(categories != null) for (String category: categories) add(sb, category);
        if(unitPrice != null) add(sb, unitPrice.toSign());
        if(unitVat != null) add(sb, unitVat.toSign());
        if(totalPrice != null) add(sb, totalPrice.toSign());
        if(totalVat != null) add(sb, totalVat.toSign());
        add(sb, productUrl);
        deleteLast(sb);
        return sb.toString();
    }
}
