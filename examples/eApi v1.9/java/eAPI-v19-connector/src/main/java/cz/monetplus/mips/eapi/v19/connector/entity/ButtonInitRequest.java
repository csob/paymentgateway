package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor @NoArgsConstructor
public class ButtonInitRequest extends SignBase  {
    private String merchantId;
    private String orderNo;
    private String clientIp;
    private Long totalAmount;
    private String currency;
    private String returnUrl;
    private String returnMethod;
    private String brand;
    private String merchantData;
    private String language;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, orderNo);
        add(sb, dttm);
        add(sb, clientIp);
        add(sb, totalAmount);
        add(sb, currency);
        add(sb, returnUrl);
        add(sb, returnMethod);
        add(sb, brand);
        add(sb, merchantData);
        add(sb, language);
        deleteLast(sb);
        return sb.toString();
    }
}
