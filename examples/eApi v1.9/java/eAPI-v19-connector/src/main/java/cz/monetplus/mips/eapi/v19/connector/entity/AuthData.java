package cz.monetplus.mips.eapi.v19.connector.entity;

public class AuthData extends ApiBase implements Signable {
        public Auth3dsBrowser browser;
        public Auth3dsSdk sdk;

        @Override
        public String toSign() {
            StringBuilder sb = new StringBuilder();
            if (null != browser) sb.append(browser.toSign());
            if (null != sdk) sb.append(sdk.toSign());
            deleteLast(sb);
            return sb.toString();
        }
    }