package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class ApplepayInitParams extends SignBase {
    private String countryCode;
    private List<String> supportedNetworks;
    private List<String> merchantCapabilities;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, countryCode);
        for (String network : supportedNetworks) add(sb, network);
        for (String capability : merchantCapabilities) add(sb, capability);
        deleteLast(sb);
        return sb.toString();
    }
}