package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OneclickEchoRequest extends SignBase {
    @NonNull
    @Getter
    private String merchantId;
    @NonNull
    @Getter
    private String origPayId;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, origPayId);
        add(sb, dttm);
        deleteLast(sb);
        return sb.toString();
    }

}