package cz.monetplus.mips.eapi.v18.connector;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import cz.monetplus.mips.eapi.v18.connector.entity.EchoReq;
import cz.monetplus.mips.eapi.v18.connector.entity.OneclickEchoReq;
import cz.monetplus.mips.eapi.v18.connector.entity.PayInitReq;
import cz.monetplus.mips.eapi.v18.connector.entity.OneclickInitReq;
import cz.monetplus.mips.eapi.v18.connector.entity.PayReq;
import cz.monetplus.mips.eapi.v18.connector.entity.CustReq;

@Path( "/api/v1.8" )
public interface INativeAPIv18Resource {

	@Path("/payment/init")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@POST
	Response paymentInit(PayInitReq req); 

	@GET
	@Path( "/payment/process/{merchantId}/{payId}/{dttm}/{signature}" ) 
	@Produces(MediaType.TEXT_HTML)
	Response paymentProcess(@PathParam("merchantId") String merchantId, 
			@PathParam("payId") String payId, 
			@PathParam("dttm") String dttm, 
			@PathParam("signature") String signature
			); 
	
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
	Response paymentReverse(PayReq req); 

	@PUT
	@Path( "/payment/close" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response paymentClose(PayReq req);
	
	@PUT
	@Path( "/payment/refund" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response paymentRefund(PayReq req);
	
	@Path( "/oneclick/echo" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	Response oneclickEcho(OneclickEchoReq req); 

	@Path( "/oneclick/init" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	Response oneclickInit(OneclickInitReq req); 

	@Path( "/oneclick/start" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	Response oneclickStart(PayReq req); 

	@GET
	@Path( "/echo/{merchantId}/{dttm}/{signature}" ) 
	@Produces(MediaType.APPLICATION_JSON)
	Response echo(@PathParam("merchantId") String merchantId, 
			@PathParam("dttm") String dttm, 
			@PathParam("signature") String signature);
	
	@POST
	@Path( "/echo" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response echo(EchoReq req);

	@POST
	@Path( "/echo/customer" ) 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response echoCustomer(CustReq req); 
	
}
