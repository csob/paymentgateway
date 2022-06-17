package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor @NoArgsConstructor
public class MallpayInitResponse extends SignBase {
    public String payId;
    public Long resultCode;
    public String resultMessage;
    public String paymentStatus;
    public String mallpayUrl;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, payId);
        add(sb, dttm);
        add(sb, resultCode);
        add(sb, resultMessage);
        add(sb, paymentStatus);
        add(sb, mallpayUrl);
        deleteLast(sb);
        return sb.toString();
    }


}
