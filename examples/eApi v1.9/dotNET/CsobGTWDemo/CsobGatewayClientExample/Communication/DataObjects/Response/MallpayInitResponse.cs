using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects.Response;

public class MallpayInitResponse : SignBase
{
    public string? PayId { get; set; }
    public int? ResultCode { get; set; }
    public string? ResultMessage { get; set; }
    public int? PaymentStatus { get; set; }

    public string? MallpayUrl { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, PayId);
        Add(sb, Dttm);
        Add(sb, ResultCode);
        Add(sb, ResultMessage);
        Add(sb, PaymentStatus);
        Add(sb, MallpayUrl);
        DeleteLast(sb);
        return sb.ToString();
    }
}