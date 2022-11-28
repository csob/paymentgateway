package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import cz.monetplus.mips.eapi.v19.connector.entity.actions.Action;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import cz.monetplus.mips.eapi.v19.connector.entity.ext.Extension;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor @NoArgsConstructor @AllArgsConstructor
public class PaymentStatusResponse extends SignBase {
    @NonNull @Getter
    private String payId;
    @NonNull @Getter
    private Integer resultCode;
    @NonNull @Getter
    private String resultMessage;
    @NonNull @Getter
    private Integer paymentStatus;
    @Getter
    private String authCode;
    @Getter
    private String statusDetail;
    @NonNull @Getter
    private Action actions;
    @Getter
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
