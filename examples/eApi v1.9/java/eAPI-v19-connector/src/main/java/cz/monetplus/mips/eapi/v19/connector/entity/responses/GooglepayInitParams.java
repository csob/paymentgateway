package cz.monetplus.mips.eapi.v19.connector.entity.responses;

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
class GooglepayInitParams extends SignBase {
    public int apiVersion = 2;
    public int apiVersionMinor = 0;
    public String paymentMethodType;
    public List<String> allowedCardNetworks = new ArrayList<>();
    public List<String> allowedCardAuthMethods = new ArrayList<>();
    public Boolean assuranceDetailsRequired;
    public Boolean billingAddressRequired;
    public String billingAddressParametersFormat;
    public String tokenizationSpecificationType;
    public String gateway;
    public String gatewayMerchantId;
    public String googlepayMerchantId;
    public String merchantName;
    public String environment;
    public String totalPriceStatus;
    public String countryCode;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, apiVersion);
        add(sb, apiVersionMinor);
        for (String value : allowedCardNetworks) {
            add(sb, value);
        }
        for (String value : allowedCardAuthMethods) {
            add(sb, value);
        }
        add(sb, googlepayMerchantId);
        add(sb, merchantName);
        add(sb, totalPriceStatus);
        deleteLast(sb);
        return sb.toString();
    }
}
