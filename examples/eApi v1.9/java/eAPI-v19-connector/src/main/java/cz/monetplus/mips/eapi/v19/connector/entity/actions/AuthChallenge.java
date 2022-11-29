package cz.monetplus.mips.eapi.v19.connector.entity.actions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import cz.monetplus.mips.eapi.v19.connector.entity.responses.Endpoint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(callSuper = false)
public
class AuthChallenge extends Action {
    private Endpoint browserChallenge;
    private SdkChallenge sdkChallenge;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        if (null != browserChallenge) add(sb, browserChallenge.toSign());
        if (null != sdkChallenge) add(sb, sdkChallenge.toSign());
        deleteLast(sb);
        return sb.toString();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class SdkChallenge extends SignBase {
        private String threeDSServerTransID;
        private String acsReferenceNumber;
        private String acsTransID;
        private String acsSignedContent;

        @Override
        public String toSign() {
            StringBuilder sb = new StringBuilder();
            add(sb, threeDSServerTransID);
            add(sb, acsReferenceNumber);
            add(sb, acsTransID);
            add(sb, acsSignedContent);
            deleteLast(sb);
            return sb.toString();
        }
    }
}