using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects.Response;

public class ButtonInitResponse : SignBase
{
    public string? PayId { get; set; }
    public int ResultCode { get; set; }
    public string? ResultMessage { get; set; }
    public long PaymentStatus { get; set; }
    public RedirectRes? Redirect { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, PayId);
        Add(sb, Dttm);
        Add(sb, ResultCode);
        Add(sb, ResultMessage);
        Add(sb, PaymentStatus);
        DeleteLast(sb);
        return sb.ToString();
    }
}