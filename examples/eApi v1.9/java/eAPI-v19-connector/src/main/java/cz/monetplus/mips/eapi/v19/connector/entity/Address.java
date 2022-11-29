package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
class Address extends ApiBase implements Signable {
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String zip;
    private String state;
    private String country;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, address1);
        add(sb, address2);
        add(sb, address3);
        add(sb, city);
        add(sb, zip);
        add(sb, state);
        add(sb, country);
        deleteLast(sb);
        return sb.toString();
    }
}
