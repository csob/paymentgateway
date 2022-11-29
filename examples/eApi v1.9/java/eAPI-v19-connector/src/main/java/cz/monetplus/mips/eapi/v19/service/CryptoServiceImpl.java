package cz.monetplus.mips.eapi.v19.service;

import cz.monetplus.mips.eapi.v19.connector.entity.SignBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

@Service
@Slf4j
public class CryptoServiceImpl implements CryptoService, InitializingBean {

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

		String mipsPublicKeyData = FileUtils.readFileToString(mipsPublicKeyFile, StandardCharsets.UTF_8);
		mipsPublicKey = initializePublicKey(mipsPublicKeyData);
	
	}
	
	private PrivateKey initializePrivateKey(File file) {
		try {
			try (PEMParser pemParser = new PEMParser(new FileReader(file))) {
				Object o = pemParser.readObject();
				JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter();
				while(o!=null) {
					if(o instanceof PEMKeyPair) {
						return jcaPEMKeyConverter.getPrivateKey(((PEMKeyPair)o).getPrivateKeyInfo());
					} else if (o instanceof PrivateKeyInfo) {
						return jcaPEMKeyConverter.getPrivateKey((PrivateKeyInfo) o);
					}
					o = pemParser.readObject();
				}
				throw new IllegalArgumentException("Invalid private key ");
			}
			catch (Exception e) {
				throw new IllegalArgumentException("Invalid private key: ", e);
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
			log.info("data to verify: '{}'", data2Verify);
			log.info("signature: '{}'", signBase.getSignature());
			return verify(mipsPublicKey, data2Verify, signBase.getSignature());
		}
		catch (Exception e) {
			log.warn("Invalid signature: ", e);
			return false;
		}
	}

	@Override
	public void createSignature(SignBase signBase) throws MipsException {
		signBase.fillDttm();
		String data2Sign = signBase.toSign();
		signBase.setSignature(sign(merchantPrivateKey, data2Sign));
		log.info("data to sign: '{}'", data2Sign);
		log.info("signature: '{}'", signBase.getSignature());
	}

	protected String sign(PrivateKey key, String plainData) throws MipsException {
		try {
			Signature instance = Signature.getInstance("SHA256withRSA");
			instance.initSign(key);
			instance.update(plainData.getBytes(StandardCharsets.UTF_8));
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
			instance.update(plainData.getBytes(StandardCharsets.UTF_8));
			return instance.verify(sign); 
		}
		catch (Exception e) {
			throw new MipsException(RespCode.INTERNAL_ERROR, "verify failed: ", e);
		}
	}
}
