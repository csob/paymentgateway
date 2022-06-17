using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class Customer : ApiBase
{
    [JsonProperty("name")] public string? Name { get; set; }

    [JsonProperty("email")] public string? Email { get; set; }

    [JsonProperty("homePhone")] public string? HomePhone { get; set; }

    [JsonProperty("workPhone")] public string? WorkPhone { get; set; }

    [JsonProperty("mobilePhone")] public string? MobilePhone { get; set; }

    [JsonProperty("account")] public Account? Account { get; set; }

    [JsonProperty("login")] public Login? Login { get; set; }


    public string? ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Name);
        Add(sb, Email);
        Add(sb, HomePhone);
        Add(sb, WorkPhone);
        Add(sb, MobilePhone);
        if (Account != null) Add(sb, Account.ToSign());
        if (Login != null) Add(sb, Login.ToSign());
        DeleteLast(sb);
        return sb.ToString();
    }
}

public class Account : SignBase
{
    [JsonProperty("createdAt")] public string? CreatedAt { get; set; }

    [JsonProperty("changedAt")] public string? ChangedAt { get; set; }

    [JsonProperty("changedPwdAt")] public string? ChangedPwdAt { get; set; }

    [JsonProperty("orderHistory")] public int? OrderHistory { get; set; }

    [JsonProperty("paymentsDay")] public int? PaymentsDay { get; set; }

    [JsonProperty("paymentsYear")] public int? PaymentsYear { get; set; }

    [JsonProperty("oneclickAdds")] public int? OneclickAdds { get; set; }

    [JsonProperty("suspicious")] public bool? Suspicious { get; set; }


    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, CreatedAt);
        Add(sb, ChangedAt);
        Add(sb, ChangedPwdAt);
        Add(sb, OrderHistory);
        Add(sb, PaymentsDay);
        Add(sb, PaymentsYear);
        Add(sb, OneclickAdds);
        Add(sb, Suspicious);
        DeleteLast(sb);
        return sb.ToString();
    }
}

public class Login : SignBase
{
    //static : ApiBase Signable

    [JsonProperty("auth")] public string? Auth { get; set; }

    [JsonProperty("authAt")] public string? AuthAt { get; set; }

    [JsonProperty("authData")] public string? AuthData { get; set; }


    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Auth);
        Add(sb, AuthAt);
        Add(sb, AuthData);
        DeleteLast(sb);
        return sb.ToString();
    }
}


/*class Customer  extends ApiBase implements Signable {
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
}*/