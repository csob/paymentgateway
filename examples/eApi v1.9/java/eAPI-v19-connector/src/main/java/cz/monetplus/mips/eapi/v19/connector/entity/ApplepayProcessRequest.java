package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
public class ApplepayProcessRequest extends SignBase {
    @NonNull
    private String merchantId;
    @NonNull
    private String payId;
    private AuthData fingerprint;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, payId);
        add(sb, dttm);
        if (null != fingerprint) add(sb, fingerprint.toSign());
        deleteLast(sb);
        return sb.toString();
    }
}
