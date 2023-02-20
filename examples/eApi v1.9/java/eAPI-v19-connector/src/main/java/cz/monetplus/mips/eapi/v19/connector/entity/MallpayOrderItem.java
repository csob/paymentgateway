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
public class MallpayOrderItem extends ApiBase implements Signable {
    private String code;
    private String ean;
    private String name;
    private String type;
    private Long quantity;
    private String variant;
    private String description;
    private String producer;
    private List<String> categories;
    private MallpayPrice unitPrice;
    private MallpayVat unitVat;
    private MallpayPrice totalPrice;
    private MallpayVat totalVat;
    private String productUrl;

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
