package cz.monetplus.mips.eapi.v19.connector.entity;

import java.util.List;

public class MallpayOrderRef  extends ApiBase implements Signable{
    public MallpayPrice totalPrice;
    public List<MallpayVat> totalVat;
    public List<MallpayOrderItemRef> items;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        if(totalPrice != null) add(sb, totalPrice.toSign());
        if(totalVat != null) totalVat.forEach(vat -> add(sb, vat.toSign()));
        if(items != null) for(MallpayOrderItemRef item: items) add(sb, item.toSign());
        deleteLast(sb);
        return sb.toString();
    }
}
