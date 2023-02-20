package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import cz.monetplus.mips.eapi.v19.connector.entity.actions.Action;
import cz.monetplus.mips.eapi.v19.connector.entity.ext.Extension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentStatusResponse extends SignBase {
    private String payId;
    private Integer resultCode;
    private String resultMessage;
    private Integer paymentStatus;
    private String authCode;
    private String statusDetail;
    private Action actions;
    private List<Extension> extensions;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, getPayId());
        add(sb, getDttm());
        add(sb, getResultCode());
        add(sb, getResultMessage());
	    add(sb, paymentStatus);
        add(sb, getAuthCode());
        add(sb, getStatusDetail());
        if (null != getActions()) add(sb, getActions().toSign());
        deleteLast(sb);
        return sb.toString();
    }
}
