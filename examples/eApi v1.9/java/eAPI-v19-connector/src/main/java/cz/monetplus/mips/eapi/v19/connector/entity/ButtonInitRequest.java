package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor @RequiredArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ButtonInitRequest extends SignBase  {
    @NonNull
    private String merchantId;
    @NonNull
    private String orderNo;
    @NonNull
    private String clientIp;
    @NonNull
    private Long totalAmount;
    @NonNull
    private String currency;
    @NonNull
    private String returnUrl;
    @NonNull
    private String returnMethod;
    private String brand;
    private String merchantData;
    @NonNull
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
