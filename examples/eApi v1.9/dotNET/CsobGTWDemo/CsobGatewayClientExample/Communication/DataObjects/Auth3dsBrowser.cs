using System.Text;
using Newtonsoft.Json;

namespace CsobGatewayClientExample.Communication.DataObjects;

public class Auth3dsBrowser : ApiBase
{
 /**
     * "Exact content of the HTTP accept headers as sent to the 3DS Requestor from the Cardholder's browser.
     * This field is limited to maximum 2048 characters  and if the total length exceeds the limit, the 3DS Server truncates the excess portion.
     * This field is required for requests where deviceChannel=02 (BRW)."
     */
 [JsonProperty("acceptHeader")]
    public string? AcceptHeader { get; set; }


 /**
     * Boolean that represents the ability of the cardholder browser to execute Java. Value is returned from
     * the navigator.javaEnabled property.
     * This field is required for requests where deviceChannel = 02 (BRW).
     */
 [JsonProperty("javaEnabled")]
    public bool JavaEnabled { get; set; }

 /**
     * Value representing the browser language as defined in IETF BCP47. The value is limited to 1-8 characters.
     * Value is returned from navigator.language property.
     * This field is required for requests where deviceChannel = 02 (BRW).
     */
 [JsonProperty("language")]
    public string? Language { get; set; }

 /**
     * Value representing the bit depth of the colour palette for displaying images, in bits per pixel. Obtained from Cardholder browser using the screen.colorDepth property. The field is limited to 1-2 characters.
     * Accepted values are:
     * 1 -> 1 bit
     * 4 -> 4 bits
     * 8 -> 8 bits
     * 15 -> 15 bits
     * 16 -> 16 bits
     * 24 -> 24 bits
     * 32 -> 32 bits
     * 48 -> 48 bits
     * <p>
     *     This field is required for requests where deviceChannel = 02 (BRW).
     */
 [JsonProperty("colorDepth")]
    public int ColorDepth { get; set; }


 /**
     * Total height of the Cardholder's screen in pixels. Value is returned from the screen.height property.
     * The value is limited to 1-6 characters.
     * This field is required for requests where deviceChannel = 02 (BRW).
     */
 [JsonProperty("screenHeight")]
    public int ScreenHeight { get; set; }


 /**
     * Total width of the Cardholder's screen in pixels. Value is returned from the screen.width property.
     * The value is limited to 1-6 characters.
     * This field is required for requests where deviceChannel = 02 (BRW).
     */
 [JsonProperty("screenWidth")]
    public int ScreenWidth { get; set; }


 /**
     * Time difference between UTC time and the Cardholder browser local time, in minutes. The field is limited to 1-5 characters
     * where the values is returned from the getTimezoneOffset() method.
     * This field is required for requests where deviceChannel = 02 (BRW).
     */
 [JsonProperty("timezone")]
    public int Timezone { get; set; }

 /**
     * Exact content of the HTTP user-agent header. The field is limited to maximum 2048 caracters. If the total length of the User-Agent
     * sent by the browser exceeds 2048 characters, the 3DS Server truncates the excess portion.
     * This field is required for requests where deviceChannel = 02 (BRW).
     */
 [JsonProperty("userAgent")]
    public string? UserAgent { get; set; }


 /**
     * "Dimensions of the challenge window that has been displayed to the Cardholder.
     * The ACS shall reply with content that is formatted to appropriately render in this window to provide
     * the best possible user experience.
     * <p>
     *     Preconfigured sizes are width X height in pixels of the window displayed in the Cardholder browser window.
     *     This is used only to prepare the CReq request and it is not part of the AReq flow. If not present it will be
     *     omitted.
     *     <p>
     *         However, when sending the Challenge Request, this field is required when deviceChannel = 02 (BRW).
     *         <p>
     *             Accepted values are:
     *             01 -> 250 x 400
     *             02 -> 390 x 400
     *             03 -> 500 x 600
     *             04 -> 600 x 400
     *             05 -> Full screen
     */
 [JsonProperty("challengeWindowSize")]
    public string? ChallengeWindowSize { get; set; }


 /**
     * Boolean that represents the ability of the cardholder browser to execute JavaScript.
     * This field is required for requests where deviceChannel = 02 (BRW).
     */
 [JsonProperty("javascriptEnabled")]
    public bool JavascriptEnabled { get; set; }

    public string ToSign()
    {
        var sb = new StringBuilder();
        Add(sb, AcceptHeader);
        Add(sb, JavaEnabled);
        Add(sb, Language);
        Add(sb, ColorDepth);
        Add(sb, ScreenHeight);
        Add(sb, ScreenWidth);
        Add(sb, Timezone);
        Add(sb, UserAgent);
        Add(sb, ChallengeWindowSize);
        Add(sb, JavascriptEnabled);
        DeleteLast(sb);
        return sb.ToString();
    }
}