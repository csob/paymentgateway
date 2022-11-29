package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@NoArgsConstructor @AllArgsConstructor
public class MallpayVat extends ApiBase implements Signable {
    private Long amount;
    private String currency;
    private Long vatRate;

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
