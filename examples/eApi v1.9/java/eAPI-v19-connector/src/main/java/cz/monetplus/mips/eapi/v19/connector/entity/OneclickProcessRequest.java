package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor @RequiredArgsConstructor
public class OneclickProcessRequest extends SignBase  {
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
