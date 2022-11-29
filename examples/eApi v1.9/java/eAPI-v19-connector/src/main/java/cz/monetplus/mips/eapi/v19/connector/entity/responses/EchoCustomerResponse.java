package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EchoCustomerResponse extends SignBase {
    private String customerId;
    private int resultCode;
    private String resultMessage;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, customerId);
        add(sb, dttm);
        add(sb, resultCode);
        add(sb, resultMessage);
        deleteLast(sb);
        return sb.toString();
    }

}
