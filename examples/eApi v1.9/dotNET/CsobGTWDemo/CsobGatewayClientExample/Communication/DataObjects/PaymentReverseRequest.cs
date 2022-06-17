using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class PaymentReverseRequest : PayReq
{
    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, PayId);
        Add(sb, Dttm);
        DeleteLast(sb);
        return sb.ToString();
    }
}