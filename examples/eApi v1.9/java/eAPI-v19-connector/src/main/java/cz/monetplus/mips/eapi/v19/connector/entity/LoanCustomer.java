package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class LoanCustomer extends ApiBase implements Signable {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LoanAddress billingAddress;
    private LoanAddress shippingAddress;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, firstName);
        add(sb, lastName);
        add(sb, email);
        add(sb, phone);
        if(null != billingAddress) add(sb, billingAddress.toSign());
        if(null != shippingAddress) add(sb, shippingAddress.toSign());
        deleteLast(sb);
        return sb.toString();
    }
}
