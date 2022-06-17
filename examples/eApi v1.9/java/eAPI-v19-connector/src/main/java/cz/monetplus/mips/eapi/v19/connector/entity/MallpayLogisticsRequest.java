package cz.monetplus.mips.eapi.v19.connector.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor @AllArgsConstructor
public class MallpayLogisticsRequest  extends SignBase {
    private String merchantId;
    private String payId;
    private String event;
    private String date;
    private MallpayOrderRef fulfilled;
    private MallpayOrderRef cancelled;
    private String deliveryTrackingNumber;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, merchantId);
        add(sb, payId);
        add(sb, event);
        add(sb, date);
        if(fulfilled != null) add(sb, fulfilled.toSign());
        if(cancelled != null) add(sb, cancelled.toSign());
        add(sb, deliveryTrackingNumber);
        add(sb, dttm);
        deleteLast(sb);
        return sb.toString();
    }
}
