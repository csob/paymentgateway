package cz.monetplus.mips.eapi.v19.connector.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
class Customer  extends ApiBase implements Signable {
    private String name;
    private String email;
    private String homePhone;
    private String workPhone;
    private String mobilePhone;
    private Account account;
    private Login login;

    @Override
    public String toSign() {
        StringBuilder sb = new StringBuilder();
        add(sb, name);
        add(sb, email);
        add(sb, homePhone);
        add(sb, workPhone);
        add(sb, mobilePhone);
        if (account != null) add(sb, account.toSign());
        if (login != null) add(sb, login.toSign());
        deleteLast(sb);
        return sb.toString();
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Account extends ApiBase implements Signable {
        private String createdAt;
        private String changedAt;
        private String changedPwdAt;
        private int orderHistory;
        private int paymentsDay;
        private int paymentsYear;
        private int oneclickAdds;
        private Boolean suspicious;


        @Override
        public String toSign() {
            StringBuilder sb = new StringBuilder();
            add(sb, createdAt);
            add(sb, changedAt);
            add(sb, changedPwdAt);
            add(sb, orderHistory);
            add(sb, paymentsDay);
            add(sb, paymentsYear);
            add(sb, oneclickAdds);
            add(sb, suspicious);
            deleteLast(sb);
            return sb.toString();
        }

        
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class Login extends ApiBase implements Signable {
        private String auth;
        private String authAt;
        private String authData;

        @Override
        public String toSign() {
            StringBuilder sb = new StringBuilder();
            add(sb, auth);
            add(sb, authAt);
            add(sb, authData);
            deleteLast(sb);
            return sb.toString();
        }
    }
}