using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects.Response;

public class EchoResponse : SignBase
{
    public int? ResultCode { get; set; }

    public string? ResultMessage { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Dttm);
        Add(sb, ResultCode);
        Add(sb, ResultMessage);
        DeleteLast(sb);
        return sb.ToString();
    }
}