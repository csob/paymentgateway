package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MallpayAddress extends ApiBase implements Signable {
    private String name;
    private String country;
    private String city;
    private String streetAddress;
    private String streetNumber;
    private String zip;
    private String addressType;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, name);
        add(sb, country);
        add(sb, city);
        add(sb, streetAddress);
        add(sb, streetNumber);
        add(sb, zip);
        add(sb, addressType);
        deleteLast(sb);
        return sb.toString();
    }
}
