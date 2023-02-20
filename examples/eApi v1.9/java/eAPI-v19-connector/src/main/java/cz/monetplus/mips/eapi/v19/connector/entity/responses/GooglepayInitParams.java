package cz.monetplus.mips.eapi.v19.connector.entity.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class GooglepayInitParams extends SignBase {
    private int apiVersion = 2;
    private int apiVersionMinor = 0;
    private String paymentMethodType;
    private List<String> allowedCardNetworks = new ArrayList<>();
    private List<String> allowedCardAuthMethods = new ArrayList<>();
    private Boolean assuranceDetailsRequired;
    private Boolean billingAddressRequired;
    private String billingAddressParametersFormat;
    private String tokenizationSpecificationType;
    private String gateway;
    private String gatewayMerchantId;
    private String googlepayMerchantId;
    private String merchantName;
    private String environment;
    private String totalPriceStatus;
    private String countryCode;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, apiVersion);
        add(sb, apiVersionMinor);
        add(sb, paymentMethodType);
        for (String value : allowedCardNetworks) {
            add(sb, value);
        }
        for (String value : allowedCardAuthMethods) {
            add(sb, value);
        }
        add(sb, assuranceDetailsRequired);
        add(sb, billingAddressRequired);
        add(sb, billingAddressParametersFormat);
        add(sb, tokenizationSpecificationType);
        add(sb, gateway);
        add(sb, gatewayMerchantId);
        add(sb, googlepayMerchantId);
        add(sb, merchantName);
        add(sb, environment);
        add(sb, totalPriceStatus);
        add(sb, countryCode);
        deleteLast(sb);
        return sb.toString();
    }
}
