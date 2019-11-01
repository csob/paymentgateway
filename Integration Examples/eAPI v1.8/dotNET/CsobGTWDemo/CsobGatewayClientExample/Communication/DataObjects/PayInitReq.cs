using System.Collections.Generic;
using System.ComponentModel;
using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects
{
    public class PayInitReq : SignBaseRequest
    {
        [JsonProperty("orderNo")]
        public string OrderNo { get; set; }

        [JsonProperty("payOperation")]
        public string PayOperation { get; set; }

        [JsonProperty("payMethod")]
        public string PayMethod { get; set; }

        [JsonProperty("totalAmount")]
        public long TotalAmount { get; set; }

        [JsonProperty("currency")]
        public string Currency { get; set; }

        [JsonProperty("closePayment")]
        public bool ClosePayment { get; set; }

        [JsonProperty("returnUrl")]
        public string ReturnUrl { get; set; }

        [JsonProperty("returnMethod")]
        public string ReturnMethod { get; set; }

        [JsonProperty("cart")]
        public List<CartItem> Cart { get; set; }

        [JsonProperty("description")]
        public string Description { get; set; }

        [JsonProperty("merchantData")]
        public string MerchantData { get; set; }

        [JsonProperty("customerId")]
        public string CustomerId { get; set; }

        [JsonProperty("language")]
        public string Language { get; set; }

        [JsonProperty("ttlSec")]
        public int TtlSec { get; set; }

        [JsonProperty("logoVersion")]
        public int LogoVersion { get; set; }

        [JsonProperty("colorSchemeVersion")]
        public int ColorSchemeVersion { get; set; }

        public override string ToSign()
        {
            StringBuilder sb = new StringBuilder();
            Add(sb, MerchantId);
            Add(sb, OrderNo);
            Add(sb, DateTime);
            Add(sb, PayOperation);
            Add(sb, PayMethod);
            Add(sb, TotalAmount);
            Add(sb, Currency);
            Add(sb, ClosePayment.ToString().ToLower());
            Add(sb, ReturnUrl);
            Add(sb, ReturnMethod);

            foreach (var item in Cart)
                Add(sb, item.ToSign());

            Add(sb, Description);
            Add(sb, MerchantData);
            Add(sb, CustomerId);
            Add(sb, Language);
            Add(sb, TtlSec);
            Add(sb, LogoVersion);
            Add(sb, ColorSchemeVersion);
            DeleteLast(sb);
            return sb.ToString();
        }
    }
}
