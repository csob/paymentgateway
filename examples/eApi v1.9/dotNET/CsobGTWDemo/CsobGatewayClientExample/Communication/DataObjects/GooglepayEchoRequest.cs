using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class GooglepayEchoRequest : SignBaseRequest
{
    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, MerchantId);
        Add(sb, Dttm);
        DeleteLast(sb);
        return sb.ToString();
    }
}