package cz.monetplus.mips.eapi.v17.service;

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

import cz.monetplus.mips.eapi.v17.connector.INativeAPIv17Resource;
import cz.monetplus.mips.eapi.v17.connector.entity.CustReq;
import cz.monetplus.mips.eapi.v17.connector.entity.CustRes;
import cz.monetplus.mips.eapi.v17.connector.entity.EchoReq;
import cz.monetplus.mips.eapi.v17.connector.entity.EchoRes;
import cz.monetplus.mips.eapi.v17.connector.entity.PayInitReq;
import cz.monetplus.mips.eapi.v17.connector.entity.PayOneclickInitReq;
import cz.monetplus.mips.eapi.v17.connector.entity.PayRefundReq;
import cz.monetplus.mips.eapi.v17.connector.entity.PayReq;
import cz.monetplus.mips.eapi.v17.connector.entity.PayRes;
import cz.monetplus.mips.eapi.v17.connector.entity.ext.Extension;


@Service
public class NativeApiV17ServiceImpl implements NativeApiV17Service  {

	static final Logger LOG = LoggerFactory.getLogger(NativeApiV17ServiceImpl.class);

	private INativeAPIv17Resource nativeApiV17Resource;
	
	@Value("${merchant.id}")
	private String merchantId;
	
	@Autowired
	private CryptoService cryptoService;
		
    @Autowired
	public NativeApiV17ServiceImpl(INativeAPIv17Resource nativeApiV17Resource) {
		this.nativeApiV17Resource = nativeApiV17Resource;
	}
    
	@Override
    public PayRes paymentInit(File initFile) throws MipsException {
		try {
			Gson gson = new GsonBuilder().create();
			PayInitReq req = gson.fromJson(FileUtils.readFileToString(initFile, "UTF-8"), PayInitReq.class);
			req.merchantId = merchantId;
			cryptoService.createSignature(req);
			Response response = nativeApiV17Resource.paymentInit(req);
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
			Response response = nativeApiV17Resource.paymentProcess(req.merchantId, req.payId, req.dttm, URLEncoder.encode(req.signature, "UTF-8"));
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
			Response response = nativeApiV17Resource.paymentStatus(req.merchantId, req.payId, req.dttm, URLEncoder.encode(req.signature, "UTF-8"));
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
			Response response = nativeApiV17Resource.paymentClose(req);
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
			Response response = nativeApiV17Resource.paymentReverse(req);
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
			Response response = nativeApiV17Resource.paymentRefund(req);
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
			Response response = nativeApiV17Resource.echo(req.merchantId, req.dttm, URLEncoder.encode(req.signature, "UTF-8"));
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
			Response response = nativeApiV17Resource.echo(req);
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
	public CustRes customerInfo(String customerId) throws MipsException {
		try {
			CustReq req = new CustReq(merchantId, customerId);
			cryptoService.createSignature(req);
			Response response = nativeApiV17Resource.customerInfo(req.merchantId, req.customerId, req.dttm, URLEncoder.encode(req.signature, "UTF-8"));
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for echo (POST) operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			CustRes res = response.readEntity(CustRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for customerInfo operation failed: ", e); 
		}
	}

	@Override
    public PayRes paymentOneclickInit(File recurrentFile) throws MipsException {
		try {
			Gson gson = new GsonBuilder().create();
			PayOneclickInitReq req = gson.fromJson(FileUtils.readFileToString(recurrentFile, "UTF-8"), PayOneclickInitReq.class);
			req.merchantId = merchantId;
			cryptoService.createSignature(req);
			Response response = nativeApiV17Resource.paymentOneclickInit(req);
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for paymentOneclickInit operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			PayRes res = response.readEntity(PayRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for paymentOneclickInit operation failed: ", e); 
		}
    }

	@Override
	public PayRes paymentOneclickStart(String payId) throws MipsException {
		try {
			PayReq req = new PayReq(merchantId, payId);
			cryptoService.createSignature(req);
			Response response = nativeApiV17Resource.paymentOneclickStart(req);
			if (response == null || response.getStatus() != 200) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "No response from nativeAPI for paymentOneclickStart operation, http response " + (response != null ? response.getStatus() : "--")); 
			}
			PayRes res = response.readEntity(PayRes.class);
			if (!cryptoService.isSignatureValid(res)) {
				throw new MipsException(RespCode.INTERNAL_ERROR, "Invalid signature for response " + res.toJson()); 
			}
			return res;
		} 
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "nativeAPI call for paymentOneclickStart operation failed: ", e); 
		}
	}
	
}
