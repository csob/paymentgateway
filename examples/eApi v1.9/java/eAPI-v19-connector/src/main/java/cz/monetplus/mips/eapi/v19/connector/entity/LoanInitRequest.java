package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor @NoArgsConstructor
public class LoanInitRequest  extends SignBase {
    private String merchantId;
    private String orderNo;
    private String purchaseId;
    private String clientIp;
    private Long totalAmount;
    private String currency;
    private String returnUrl;
    private String returnMethod;
    private LoanCustomer customer;
    private String merchantData;
    private String language;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, orderNo);
        add(sb, purchaseId);
        add(sb, dttm);
        add(sb, clientIp);
        add(sb, totalAmount);
        add(sb, currency);
        add(sb, returnUrl);
        add(sb, returnMethod);
        add(sb, merchantData);
        add(sb, language);
        deleteLast(sb);
        return sb.toString();
    }

}
