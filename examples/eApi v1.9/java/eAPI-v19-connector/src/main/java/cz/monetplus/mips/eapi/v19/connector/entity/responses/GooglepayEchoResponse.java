package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GooglepayEchoResponse extends SignBase {

    private Integer resultCode;
    private String resultMessage;
    private GooglepayInitParams initParams;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, getDttm());
        add(sb, getResultCode());
        add(sb, getResultMessage());
        if (getInitParams() != null) {
            add(sb, getInitParams().toSign());
        }
        deleteLast(sb);
        return sb.toString();
    }
}
