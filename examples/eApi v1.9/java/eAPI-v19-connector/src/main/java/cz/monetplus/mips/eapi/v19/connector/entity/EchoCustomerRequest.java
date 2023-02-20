package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class EchoCustomerRequest  extends SignBase {
    @NonNull
    private String merchantId;
    @NonNull
    private String customerId;


    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, customerId);
        add(sb, dttm);
        deleteLast(sb);
        return sb.toString();
    }
}
