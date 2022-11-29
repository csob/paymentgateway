package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class LoanLogisticsRequest  extends SignBase {
    @NonNull
    private String merchantId;
    @NonNull
    private String payId;
    @NonNull
    private String event;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, payId);
        add(sb, event);
        add(sb, dttm);
        deleteLast(sb);
        return sb.toString();
    }

}
