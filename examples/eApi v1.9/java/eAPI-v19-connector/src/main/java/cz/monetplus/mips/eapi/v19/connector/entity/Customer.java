package cz.monetplus.mips.eapi.v19.connector.entity;

class Customer  extends ApiBase implements Signable {
    public String name;
    public String email;
    public String homePhone;
    public String workPhone;
    public String mobilePhone;
    public Account account;
    public Login login;

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

    public static class Account extends ApiBase implements Signable {
        public String createdAt;
        public String changedAt;
        public String changedPwdAt;
        public int orderHistory;
        public int paymentsDay;
        public int paymentsYear;
        public int oneclickAdds;
        public Boolean suspicious;


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

    public static class Login extends ApiBase implements Signable {
        public String auth;
        public String authAt;
        public String authData;

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