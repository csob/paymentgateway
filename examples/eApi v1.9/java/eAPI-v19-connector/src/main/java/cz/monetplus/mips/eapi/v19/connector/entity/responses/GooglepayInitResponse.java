package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import cz.monetplus.mips.eapi.v19.connector.entity.actions.Action;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GooglepayInitResponse extends SignBase {
    @NonNull
    private String payId;
    @NonNull
    private Integer resultCode;
    @NonNull
    private String resultMessage;
    private Integer paymentStatus;
    private String statusDetail;
    private List<Action> actions = new LinkedList<>();

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, getPayId());
        add(sb, getDttm());
        add(sb, getResultCode());
        add(sb, getResultMessage());
        add(sb, getPaymentStatus());
        add(sb, getStatusDetail());
        if (null != getActions()) for (Action a : getActions()) add(sb, a.toSign());
        deleteLast(sb);
        return sb.toString();
    }
}
