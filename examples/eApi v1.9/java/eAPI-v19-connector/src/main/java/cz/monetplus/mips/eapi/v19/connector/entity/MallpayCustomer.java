package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MallpayCustomer extends ApiBase implements Signable {
    private String firstName;
    private String lastName;
    private String fullName;
    private String titleBefore;
    private String titleAfter;
    private String email;
    private String phone;
    private String tin;
    private String vatin;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, firstName);
        add(sb, lastName);
        add(sb, fullName);
        add(sb, titleBefore);
        add(sb, titleAfter);
        add(sb, email);
        add(sb, phone);
        add(sb, tin);
        add(sb, vatin);
        deleteLast(sb);
        return sb.toString();
    }

}
