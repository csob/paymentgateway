using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects
{
    public class ApiBase
    {
        public const char SEP = '|';

        public const int MERCHANT_ID_MAX_LEN = 10;
        public const int ORDER_NO_MAX_LEN = 10;
        public const int PAY_ID_MAX_LEN = 15;
        public const int RETURNURL_MAX_LEN = 300;
        public const int CART_ITEM_NAME_MAX_LEN = 20;
        public const int CART_ITEM_DESC_MAX_LEN = 40;
        public const int DESCRIPTION_MAX_LEN = 255;
        public const int CUST_ID_MAX_LEN = 50;
        public const int MERCHANTDATA_MAX_LEN = 255;
        public const int SIGNATURE_MAX_LEN = 500;
        public const int CARDNAME_MAX_LEN = 20;

        public static readonly string[] PAY_OPER_ALLOWED_VALUES = new string[] { "payment" };
	    public static readonly string[] PAY_METHOD_ALLOWED_VALUES = new string[] { "card" };
	    public static readonly string[] RETURN_METHOD_ALLOWED_VALUES = new string[] { "POST", "GET" };
	    public static readonly string[] CURRENCY_ALLOWED_VALUES = new string[] { "CZK", "EUR", "USD", "GBP" };
	    public static readonly string[] LANGUAGE_ALLOWED_VALUES = new string[] { "CZ", "EN", "DE", "SK" };
	
	    public const string DTTM_FORMAT = "yyyyMMddHHmmss";
	
	    protected void Add(StringBuilder sb, string value)
        {
            if (value != null)
            {
                sb.Append(value).Append(SEP);
            }
        }

        protected void Add(StringBuilder sb, bool value)
        {
            sb.Append(value).Append(SEP);
        }

        protected void Add(StringBuilder sb, int? value)
        {
            if (value != null)
            {
                sb.Append(value).Append(SEP);
            }
        }

        protected void Add(StringBuilder sb, int value)
        {
            sb.Append(value).Append(SEP);
        }

        protected void Add(StringBuilder sb, long value)
        {
            sb.Append(value).Append(SEP);
        }

        protected void DeleteLast(StringBuilder sb)
        {
            if (SEP == sb[sb.Length - 1])
            {
                sb.Remove(sb.Length - 1, 1);
            }
        }
    }
}
