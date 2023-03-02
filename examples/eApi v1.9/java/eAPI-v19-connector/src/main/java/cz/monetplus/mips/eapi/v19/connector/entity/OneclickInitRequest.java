package cz.monetplus.mips.eapi.v19.connector.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import cz.monetplus.mips.eapi.v19.connector.entity.ext.Extension;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor @AllArgsConstructor
public class OneclickInitRequest extends SignBase  {
	@NonNull
	private String merchantId;
	@NonNull
	private String origPayId;
	@NonNull
	private String orderNo;
	private String payMethod;
	private String clientIp;
	private Long totalAmount;
	private String currency;
	private Boolean closePayment;
	@NonNull
	private String returnUrl;
	@NonNull
	private String returnMethod;
	private Customer customer;
	private Order order;
	private Boolean clientInitiated;
	private Boolean sdkUsed;
	private String merchantData;
	private List<Extension> extensions;
   	private Integer ttlSec;

	@Override
	public String toSign() {
		StringBuilder sb = new StringBuilder();
		add(sb, merchantId);
		add(sb, origPayId);
		add(sb, orderNo);
		add(sb, dttm);
		add(sb, clientIp);
		add(sb, totalAmount);
		add(sb, currency);
		add(sb, closePayment);
		add(sb, returnUrl);
		add(sb, returnMethod);
		if (null != customer) add(sb, customer.toSign());
		if (null != order) add(sb, order.toSign());
		add(sb, clientInitiated);
		add(sb, sdkUsed);
		add(sb, merchantData);
                add(sb, ttlSec);
		deleteLast(sb);
		return sb.toString();
	}
}
