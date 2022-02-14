using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects
{
    public class EchoRes : SignBase
    {
        public int ResultCode { get; set; }
        public string ResultMessage { get; set; }
        
        public override string ToSign()
        {
            var sb = new StringBuilder();
            Add(sb, DateTime);
            Add(sb, ResultCode);
            Add(sb, ResultMessage);
            DeleteLast(sb);
            return sb.ToString();
        }
    }
}
