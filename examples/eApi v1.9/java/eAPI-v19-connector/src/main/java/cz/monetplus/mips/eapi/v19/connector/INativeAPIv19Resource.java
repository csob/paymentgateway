package cz.monetplus.mips.eapi.v19.connector;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cz.monetplus.mips.eapi.v19.connector.entity.ApplepayEchoRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.ApplepayInitRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.ApplepayProcessRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.ButtonInitRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.EchoCustomerRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.EchoRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.GooglepayEchoRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.GooglepayInitRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.GooglepayProcessRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.LoanCancelRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.LoanInitRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.LoanLogisticsRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.LoanRefundRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.MallpayCancelRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.MallpayInitRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.MallpayLogisticsRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.MallpayRefundRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.OneclickEchoRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.OneclickInitRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.OneclickProcessRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.PaymentCloseRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.PaymentInitRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.PaymentRefundRequest;
import cz.monetplus.mips.eapi.v19.connector.entity.PaymentReverseRequest;


@Path( "/api/v1.9" )
public interface INativeAPIv19Resource {

	@Path("/payment/init")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    Response paymentInit(PaymentInitRequest req);

	@GET
	@Path( "/payment/status/{merchantId}/{payId}/{dttm}/{signature}" ) 
	@Produces(MediaType.APPLICATION_JSON)
	Response paymentStatus(@PathParam("merchantId") String merchantId, 
			@PathParam("payId") String payId, 
			@PathParam("dttm") String dttm, 
			@PathParam("signature") String signature); 

	@PUT
	@Path( "/payment/reverse" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response paymentReverse(PaymentReverseRequest req); 

	@GET
	@Path("/payment/process/{merchantId}/{payId}/{dttm}/{signature}")
	@Produces(MediaType.TEXT_HTML)
	Response paymentProcess(@PathParam("merchantId") String merchantId,
	@PathParam("payId") String payId, 
	@PathParam("dttm") String dttm, 
	@PathParam("signature") String signature);

	@PUT
	@Path( "/payment/close" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response paymentClose(PaymentCloseRequest req);
	
	@PUT
	@Path( "/payment/refund" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response paymentRefund(PaymentRefundRequest req);

	@Path( "/oneclick/echo" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	Response oneclickEcho(OneclickEchoRequest req);

	@Path( "/oneclick/init" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	Response oneclickInit(OneclickInitRequest req);

	@Path( "/oneclick/process" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	Response oneclickProcess(OneclickProcessRequest req);

	
	@GET
	@Path( "/echo/{merchantId}/{dttm}/{signature}" ) 
	@Produces(MediaType.APPLICATION_JSON)
	Response echo(@PathParam("merchantId") String merchantId, 
			@PathParam("dttm") String dttm, 
			@PathParam("signature") String signature);
	
	@POST
	@Path( "/echo" ) 
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	Response echo(EchoRequest req);

	@POST
	@Path( "/echo/customer" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response echoCustomer(EchoCustomerRequest req); 
	
	
	@Path("/button/init")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	Response buttonInit(ButtonInitRequest req); 

	
	@POST
	@Path( "/googlepay/init" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response googlepayInit(GooglepayInitRequest req);

	@POST
	@Path("/googlepay/echo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response googlepayEcho(GooglepayEchoRequest req);


	@POST
	@Path( "/googlepay/process" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response googlepayProcess(GooglepayProcessRequest req);

	@POST
	@Path("/applepay/echo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response applepayEcho(ApplepayEchoRequest req);
	
	@POST
	@Path( "/applepay/init" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response applepayInit(ApplepayInitRequest req);

	@POST
	@Path( "/applepay/process" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response applepayProcess(ApplepayProcessRequest request);

	
	@POST
	@Path( "/loan/init" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response loanInit(LoanInitRequest req); 

	@PUT
	@Path( "/loan/logistics" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response loanLogistics(LoanLogisticsRequest req); 

	@PUT
	@Path( "/loan/cancel" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response loanCancel(LoanCancelRequest req); 

	@PUT
	@Path( "/loan/refund" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response loanRefund(LoanRefundRequest req);

	
	@POST
	@Path( "/mallpay/init" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response mallpayInit(MallpayInitRequest req);
	
	@PUT
	@Path( "/mallpay/logistics" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response mallpayLogistics(MallpayLogisticsRequest req); 

	@PUT
	@Path( "/mallpay/cancel" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response mallpayCancel(MallpayCancelRequest req); 

	@PUT
	@Path( "/mallpay/refund" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response mallpayRefund(MallpayRefundRequest req); 
	
}
