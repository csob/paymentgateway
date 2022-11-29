package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInitResponse extends SignBase {

    private String payId;
    private Integer resultCode;
    private String resultMessage;
    private Integer paymentStatus;
    private String authCode;
    private String customerCode;
    private String statusDetail;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, getPayId());
        add(sb, getDttm());
        add(sb, getResultCode());
        add(sb, getResultMessage());
        add(sb, getPaymentStatus());
        add(sb, getAuthCode());
        add(sb, getCustomerCode());
        add(sb, getStatusDetail());
        deleteLast(sb);
        return sb.toString();
    }
}
