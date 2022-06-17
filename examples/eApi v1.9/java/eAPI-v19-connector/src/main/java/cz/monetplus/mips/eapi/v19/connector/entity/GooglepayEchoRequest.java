package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@Data
public class GooglepayEchoRequest extends SignBase {

    @NonNull
    private String merchantId;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, dttm);
        return sb.toString();
    }
    
}
