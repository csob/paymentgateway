package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor @NoArgsConstructor
class ApplepayInitParams extends SignBase {
    @NonNull
    private String countryCode;
    @NonNull
    private List<String> supportedNetworks;
    @NonNull
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