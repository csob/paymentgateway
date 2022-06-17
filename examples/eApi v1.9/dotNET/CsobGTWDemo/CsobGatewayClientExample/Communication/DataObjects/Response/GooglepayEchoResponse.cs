using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects.Response;

public class GooglepayEchoResponse : SignBase
{
    public int? ResultCode { get; set; }

    public string? ResultMessage { get; set; }

    public GooglepayInitParams? InitParams { get; set; }


    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Dttm);
        Add(sb, ResultCode);
        Add(sb, ResultMessage);
        if (InitParams != null) Add(sb, InitParams.ToSign());
        DeleteLast(sb);
        return sb.ToString();
    }
}