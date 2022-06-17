using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects.Response;

public class EchoCustomerResponse : SignBase
{
    public string? CustomerId { get; set; }
    public long ResultCode { get; set; }
    public string? ResultMessage { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, CustomerId);
        Add(sb, Dttm);
        Add(sb, ResultCode);
        Add(sb, ResultMessage);
        DeleteLast(sb);
        return sb.ToString();
    }
}