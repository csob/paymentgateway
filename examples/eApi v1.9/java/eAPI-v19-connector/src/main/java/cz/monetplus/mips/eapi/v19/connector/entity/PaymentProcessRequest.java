package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentProcessRequest extends SignBase {
    @Getter
    public String merchantId;
    @Getter
    public String payId;

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
