package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ApplepayEchoRequest extends SignBase {
    @NonNull
    private String merchantId;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, dttm);
        deleteLast(sb);
        return sb.toString();
    }  
}
