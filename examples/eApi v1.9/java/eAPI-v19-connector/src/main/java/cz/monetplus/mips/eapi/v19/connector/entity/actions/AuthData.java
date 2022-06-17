package cz.monetplus.mips.eapi.v19.connector.entity.actions;

import cz.monetplus.mips.eapi.v19.connector.entity.Auth3dsBrowser;
import cz.monetplus.mips.eapi.v19.connector.entity.Auth3dsSdk;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;

public class AuthData extends SignBase {
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
