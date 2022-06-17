package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import cz.monetplus.mips.eapi.v19.connector.entity.actions.Endpoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor @NoArgsConstructor
public class ButtonInitResponse extends SignBase {
    private String payId;
    private Long resultCode;
    private String resultMessage;
    private Integer paymentStatus;
    private Endpoint redirect;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, payId);
        add(sb, dttm);
        add(sb, resultCode);
        add(sb, resultMessage);
        add(sb, paymentStatus);
        if(null != redirect) add(sb, redirect.toSign());
        deleteLast(sb);
        return sb.toString();
    }
}
