package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor @NoArgsConstructor
public class MallpayOrder extends ApiBase implements Signable {
    private MallpayPrice totalPrice;
    private List<MallpayVat> totalVat = new ArrayList<>();
    private List<MallpayAddress> addresses = new ArrayList<>();
    private String deliveryType;
    private String carrierId;
    private String carrierCustom;
    private List<MallpayOrderItem> items;

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
