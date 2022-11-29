package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@AllArgsConstructor @NoArgsConstructor
public class MallpayOrderItemRef extends ApiBase  implements Signable {
    private String code;
    private String ean;
    private String name;
    private String type;
    private Long quantity;

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
