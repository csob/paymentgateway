package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import java.util.ArrayList;
import java.util.List;

import cz.monetplus.mips.eapi.v19.connector.entity.actions.Action;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class PaymentResponse extends SignBase {
    public String payId;
    public Long resultCode;
    public String resultMessage;
    public Long paymentStatus;
    public String authCode;
    public String statusDetail;
    public List<Action> actions = new ArrayList<>();

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
