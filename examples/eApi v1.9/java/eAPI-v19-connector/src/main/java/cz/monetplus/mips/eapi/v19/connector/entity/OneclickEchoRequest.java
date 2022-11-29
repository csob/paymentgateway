package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
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