package cz.monetplus.mips.eapi.v19.connector.entity.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;

class AuthInit extends Action {
    public Endpoint browserInit;
    public SdkInit sdkInit;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        if (null != browserInit) add(sb, browserInit.toSign());
        if (null != sdkInit) add(sb, sdkInit.toSign());
        deleteLast(sb);
        return sb.toString();
    }

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    public static class SdkInit extends SignBase {
        public String directoryServerID;
        public String schemeId;
        public String messageVersion;

        @Override
        public String toSign() {
            StringBuilder sb = new StringBuilder();
            add(sb, directoryServerID);
            add(sb, schemeId);
            add(sb, messageVersion);
            deleteLast(sb);
            return sb.toString();
        }
    }
}
