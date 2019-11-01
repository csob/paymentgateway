package cz.monetplus.mips.eapi.v18.service;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cz.monetplus.mips.eapi.v18.connector.entity.SignBase;

@Service
public class CryptoServiceImpl implements CryptoService, InitializingBean {

	static final Logger LOG = LoggerFactory.getLogger(CryptoServiceImpl.class);
	
	@Value("${merchant.private.key.filename}")
	private String merchantPrivateKeyFilename;

	private PrivateKey merchantPrivateKey;

	@Value("${mips.public.key.filename}")
	private String mipsPublicKeyFilename;

	private PublicKey mipsPublicKey;
	
	@Override
	public void afterPropertiesSet() throws Exception {

		if (Security.getProvider("BC") == null) {
		    Security.addProvider(new BouncyCastleProvider());
		}

		File merchantPrivateKeyFile = new File(merchantPrivateKeyFilename);
		if (!merchantPrivateKeyFile.isFile() || !merchantPrivateKeyFile.canRead()) {
			throw new IllegalArgumentException("Unable to load merchant private key from " + merchantPrivateKeyFile.getAbsolutePath());
		}

		merchantPrivateKey = initializePrivateKey(merchantPrivateKeyFile);
		
		File mipsPublicKeyFile = new File(mipsPublicKeyFilename);
		if (!mipsPublicKeyFile.isFile() || !mipsPublicKeyFile.canRead()) {
			throw new IllegalArgumentException("Unable to load mips public key from " + mipsPublicKeyFile.getAbsolutePath());
		}

		String mipsPublicKeyData = FileUtils.readFileToString(mipsPublicKeyFile);
		mipsPublicKey = initializePublicKey(mipsPublicKeyData);
	
	}
	
	private PrivateKey initializePrivateKey(File file) {
		try {
			PEMReader reader = null;
			try {
				Reader fileReader = new FileReader(file);
				reader = new PEMReader(fileReader);
				KeyPair keyPair= (KeyPair)reader.readObject();
				return keyPair.getPrivate();
			}
			catch (Exception e) {
				throw new IllegalArgumentException("Invalid private key: ", e);
			}
			finally {
				reader.close();
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private PublicKey initializePublicKey(String data) {
		try {
			data = StringUtils.remove(data, "-----BEGIN PUBLIC KEY-----");
			data = StringUtils.remove(data, "-----END PUBLIC KEY-----");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(data));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Invalid public key: ", e);
		}
	}

	@Override
	public boolean isSignatureValid(SignBase signBase) {
		try {
			String data2Verify = signBase.toSign();
			LOG.info("data to verify: '{}'", data2Verify);
			LOG.info("signature: '{}'", signBase.signature);
			return verify(mipsPublicKey, data2Verify, signBase.signature);
		}
		catch (Exception e) {
			LOG.warn("Invalid signature: ", e);
			return false;
		}
	}

	@Override
	public void createSignature(SignBase signBase) throws MipsException {
		signBase.fillDttm();
		String data2Sign = signBase.toSign();
		signBase.signature = sign(merchantPrivateKey, data2Sign);
		LOG.info("data to sign: '{}'", data2Sign);
		LOG.info("signature: '{}'", signBase.signature);
	}

	protected String sign(PrivateKey key, String plainData) throws MipsException {
		try {
			Signature instance = Signature.getInstance("SHA256withRSA");
			instance.initSign(key);
			instance.update(plainData.getBytes("UTF-8"));
			byte[] signature = instance.sign();
			return Base64.encodeBase64String(signature);
		}
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "sign failed: ", e);
		}
	}

	protected boolean verify(PublicKey key, String plainData, String signature) throws MipsException {
		try {
			byte[] sign = Base64.decodeBase64(signature);
			Signature instance = Signature.getInstance("SHA256withRSA");
			instance.initVerify(key);
			instance.update(plainData.getBytes("UTF-8"));
			return instance.verify(sign); 
		}
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "verify failed: ", e);
		}
	}
	
}
