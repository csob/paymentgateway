using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CsobGatewayClientExample.Communication.DataObjects
{
    public class PayRes : SignBase
    {
        public string PayId { get; set; }

        public int ResultCode { get; set; }

        public string ResultMessage { get; set; }

        public int PaymentStatus { get; set; }

        public string AuthCode { get; set; }

        public List<string> Extensions { get; set; }

        public PayRes()
        {
        }

        public override string ToSign()
        {
            var sb = new StringBuilder();
            Add(sb, PayId);
            Add(sb, DateTime);
            Add(sb, ResultCode);
            Add(sb, ResultMessage);
            Add(sb, PaymentStatus);
            Add(sb, AuthCode);
            DeleteLast(sb);
            return sb.ToString();
        }

    }
}
