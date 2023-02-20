package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AuthData extends ApiBase implements Signable {
        private Auth3dsBrowser browser;
        private Auth3dsSdk sdk;

        @Override
        public String toSign() {
            StringBuilder sb = new StringBuilder();
            if (null != browser) sb.append(browser.toSign());
            if (null != sdk) sb.append(sdk.toSign());
            deleteLast(sb);
            return sb.toString();
        }
    }