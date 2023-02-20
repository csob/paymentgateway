package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PaymentProcessRequest extends SignBase {
    @Getter
    private String merchantId;
    @Getter
    private String payId;

    public PaymentProcessRequest(String merchantId, String payId) {
        this.merchantId = merchantId;
        this.payId = payId;
    }

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, payId);
        add(sb, dttm);
        deleteLast(sb);
        return sb.toString();
    }
}
