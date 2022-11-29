package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor @NoArgsConstructor
public class MallpayOrderRef  extends ApiBase implements Signable{
    private MallpayPrice totalPrice;
    private List<MallpayVat> totalVat;
    private List<MallpayOrderItemRef> items;

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
