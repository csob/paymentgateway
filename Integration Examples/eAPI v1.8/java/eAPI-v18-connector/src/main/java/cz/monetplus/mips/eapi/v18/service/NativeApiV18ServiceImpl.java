package cz.monetplus.mips.eapi.v18.service;

import java.io.File;
import java.net.URI;
import java.net.URLEncoder;

import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cz.monetplus.mips.eapi.v18.connector.INativeAPIv18Resource;
import cz.monetplus.mips.eapi.v18.connector.entity.CustReq;
import cz.monetplus.mips.eapi.v18.connector.entity.CustRes;
import cz.monetplus.mips.eapi.v18.connector.entity.EchoReq;
import cz.monetplus.mips.eapi.v18.connector.entity.EchoRes;
import cz.monetplus.mips.eapi.v18.connector.entity.OneclickEchoReq;
import cz.monetplus.mips.eapi.v18.connector.entity.OneclickEchoRes;
import cz.monetplus.mips.eapi.v18.connector.entity.OneclickInitReq;
import cz.monetplus.mips.eapi.v18.connector.entity.PayInitReq;
import cz.monetplus.mips.eapi.v18.connector.entity.PayRefundReq;
import cz.monetplus.mips.eapi.v18.connector.entity.PayReq;
import cz.monetplus.mips.eapi.v18.connector.entity.PayRes;
import cz.monetplus.mips.eapi.v18.connector.entity.ext.Extension;


@Service
public class NativeApiV18ServiceImpl implements NativeApiV18Service  {

	static final Logger LOG = LoggerFactory.getLogger(NativeApiV18ServiceImpl.class);

	private INativeAPIv18Resource nativeApiResource;
	
	@Value("${merchant.id}")
	private String merchantId;
	
	@Autowired
	private CryptoService cryptoService;
		
    @Autowired
	public NativeApiV18ServiceImpl(INativeAPIv18Resource nativeApiResource) {
		this.nativeApiResource = nativeApiResource;
	}
    
	@Override
    public PayRes paymentInit(File initFile) throws MipsException {
		try {
			Gson gson = new GsonBuilder().create();
			PayInitReq req = gson.fromJson(FileUtils.readFileToString(initFile, "UTF-8"), PayInitReq.class);
			req.merchantId = merchantId;
			cryptoService.createSignature(req);
			Response response = nativeApiResource.paymentInit(req);
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for paymentInit operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			PayRes res = response.readEntity(PayRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for paymentInit operation failed: ", e); 
		}
    }

    
	@Override
	public String paymentProcess(String payId) throws MipsException {
		try {
			PayReq req = new PayReq(merchantId, payId);
			cryptoService.createSignature(req);
			Response response = nativeApiResource.paymentProcess(req.merchantId, req.payId, req.dttm, URLEncoder.encode(req.signature, "UTF-8"));
			if (response == null || response.getStatus() != 303) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Expected 303 http response from nativeAPI for paymentProcess operation, got response " 
						+ (response != null ? response.getStatus() : "--") + ", payment not found or expired?"); 
			}
			URI uri = response.getLocation();
			if (uri == null) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Missing location header in response of paymentProcess operation"); 
			}
			return uri.toString();
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for paymentProcess operation failed: ", e); 
		}
	}

	@Override
	public PayRes paymentStatus(String payId) throws MipsException {
		try {
			PayReq req = new PayReq(merchantId, payId);
			cryptoService.createSignature(req);
			Response response = nativeApiResource.paymentStatus(req.merchantId, req.payId, req.dttm, URLEncoder.encode(req.signature, "UTF-8"));
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for paymentStatus operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			PayRes res = response.readEntity(PayRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			if (res.extensions != null) {
				for (Extension ext : res.extensions) {
					if (!cryptoService.isSignatureValid(ext)) {
						throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response extension " + ext.toJson()); 
					}
				}
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for paymentStatus operation failed: ", e); 
		}
	}

	@Override
	public PayRes paymentClose(String payId) throws MipsException {
		try {
			PayReq req = new PayReq(merchantId, payId);
			cryptoService.createSignature(req);
			Response response = nativeApiResource.paymentClose(req);
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for paymentClose operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			PayRes res = response.readEntity(PayRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for paymentClose operation failed: ", e); 
		}
	}

	@Override
	public PayRes paymentReverse(String payId) throws MipsException {
		try {
			PayReq req = new PayReq(merchantId, payId);
			cryptoService.createSignature(req);
			Response response = nativeApiResource.paymentReverse(req);
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for paymentReverse operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			PayRes res = response.readEntity(PayRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for paymentReverse operation failed: ", e); 
		}
	}

	
	@Override
	public PayRes paymentRefund(String payId, Long amount) throws MipsException {
		try {
			PayRefundReq req = new PayRefundReq(merchantId, payId, amount);
			cryptoService.createSignature(req);
			Response response = nativeApiResource.paymentRefund(req);
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for paymentRefund operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			PayRes res = response.readEntity(PayRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for paymentRefund operation failed: ", e); 
		}
	}

	
	@Override
	public EchoRes echoGet() throws MipsException {
		try {
			EchoReq req = new EchoReq(merchantId);
			cryptoService.createSignature(req);
			Response response = nativeApiResource.echo(req.merchantId, req.dttm, URLEncoder.encode(req.signature, "UTF-8"));
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for echo (GET) operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			EchoRes res = response.readEntity(EchoRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for echo (GET) operation failed: ", e); 
		}
	}

	@Override
	public EchoRes echoPost() throws MipsException {
		try {
			EchoReq req = new EchoReq(merchantId);
			cryptoService.createSignature(req);
			Response response = nativeApiResource.echo(req);
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for echo (POST) operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			EchoRes res = response.readEntity(EchoRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for echo (POST) operation failed: ", e); 
		}
	}
	
	@Override
	public CustRes echoCustomer(String customerId) throws MipsException {
		try {
			CustReq req = new CustReq(merchantId, customerId);
			cryptoService.createSignature(req);
			Response response = nativeApiResource.echoCustomer(req);
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for echoCustomer (POST) operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			CustRes res = response.readEntity(CustRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for echoCustomer operation failed: ", e); 
		}
	}

	@Override
    public OneclickEchoRes oneclickEcho(String origPayId) throws MipsException {
		try {
			OneclickEchoReq req = new OneclickEchoReq(merchantId, origPayId);
			cryptoService.createSignature(req);
			Response response = nativeApiResource.oneclickEcho(req);
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for oneclickEcho operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			OneclickEchoRes res = response.readEntity(OneclickEchoRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for oneclickEcho operation failed: ", e); 
		}
    }

	@Override
    public PayRes oneclickInit(File oneclickFile) throws MipsException {
		try {
			Gson gson = new GsonBuilder().create();
			OneclickInitReq req = gson.fromJson(FileUtils.readFileToString(oneclickFile, "UTF-8"), OneclickInitReq.class);
			req.merchantId = merchantId;
			cryptoService.createSignature(req);
			Response response = nativeApiResource.oneclickInit(req);
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for oneclickInit operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			PayRes res = response.readEntity(PayRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for oneclickInit operation failed: ", e); 
		}
    }

	@Override
	public PayRes oneclickStart(String payId) throws MipsException {
		try {
			PayReq req = new PayReq(merchantId, payId);
			cryptoService.createSignature(req);
			Response response = nativeApiResource.oneclickStart(req);
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for oneclickStart operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			PayRes res = response.readEntity(PayRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for oneclickStart operation failed: ", e); 
		}
	}
	
}
