package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import cz.monetplus.mips.eapi.v19.connector.entity.actions.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor @NoArgsConstructor
public class GooglepayProcessResponse extends SignBase {
    private String payId;
    private Long resultCode;
    private String resultMessage;
    private Long paymentStatus;
    private String authCode;
    private String statusDetail;
    private List<Action> actions = new ArrayList<>();

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, payId);
        add(sb, dttm);
        add(sb, resultCode);
        add(sb, resultMessage);
        add(sb, paymentStatus);
        add(sb, authCode);
        add(sb, statusDetail);
        for(Action a : actions) add(sb, a.toSign());
        deleteLast(sb);
        return sb.toString();
    }
}
