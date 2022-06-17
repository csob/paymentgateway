package cz.monetplus.mips.eapi.v19.connector.entity;

import java.util.ArrayList;
import java.util.List;

public class MallpayOrder extends ApiBase implements Signable {
    public MallpayPrice totalPrice;
    public List<MallpayVat> totalVat = new ArrayList<>();
    public List<MallpayAddress> addresses = new ArrayList<>();
    public String deliveryType;
    public String carrierId;
    public String carrierCustom;
    public List<MallpayOrderItem> items;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        if(totalPrice != null) add(sb, totalPrice.toSign());
        if(totalVat != null) for (MallpayVat vat : totalVat) add(sb, vat.toSign());
        if(addresses != null) for (MallpayAddress address : addresses) add(sb, address.toSign());
        add(sb, deliveryType);
        add(sb, carrierId);
        add(sb, carrierCustom);
        if(items != null) for (MallpayOrderItem item : items) add(sb, item.toSign());
        deleteLast(sb);
        return sb.toString();
    }
}
