package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import cz.monetplus.mips.eapi.v19.connector.entity.actions.Action;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.*;

import java.util.LinkedList;
import java.util.List;


@EqualsAndHashCode(callSuper = false)
@Data
@RequiredArgsConstructor @NoArgsConstructor
public class PaymentInitResponse extends SignBase {
    @NonNull @Getter
    private String payId;
    @NonNull @Getter
    private Integer resultCode;
    @NonNull @Getter
    private String resultMessage;
    @Getter 
    private Integer paymentStatus;
    @Getter
    private String authCode;
    @Getter
    private String customerCode;
    @Getter
    private String statusDetail;

    @Getter
    private List<Action> actions = new LinkedList<>();

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
