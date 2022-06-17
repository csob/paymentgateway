using System.Text;

namespace CsobGatewayClientExample.Communication.DataObjects.Response;

public class ApplepayEchoResponse : SignBase
{
    public int ResultCode { get; set; }
    public string? ResultMessage { get; set; }

    public ApplepayInitParams? InitParams { get; set; }

    public override string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, Dttm);
        Add(sb, ResultCode);
        Add(sb, ResultMessage);
        if (null != InitParams) Add(sb, InitParams.ToSign());
        DeleteLast(sb);
        return sb.ToString();
    }
}