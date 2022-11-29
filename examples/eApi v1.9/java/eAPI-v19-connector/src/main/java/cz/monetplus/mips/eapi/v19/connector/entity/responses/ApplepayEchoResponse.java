package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor @NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplepayEchoResponse extends SignBase {
    private Integer resultCode;
    private String resultMessage;
    private ApplepayInitParams initParams;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, getDttm());
        add(sb, getResultCode());
        add(sb, getResultMessage());
        if(null != initParams) add(sb, initParams.toSign());
        deleteLast(sb);
        return sb.toString();
    }
}
