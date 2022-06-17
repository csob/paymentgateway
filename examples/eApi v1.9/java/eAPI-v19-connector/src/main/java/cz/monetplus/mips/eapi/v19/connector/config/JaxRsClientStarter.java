package cz.monetplus.mips.eapi.v19.connector.config;

import com.cloudcontrolled.api.client.security.DumbX509TrustManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.configuration.security.FiltersType;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.List;

@Slf4j
public class JaxRsClientStarter<T> {
	

	public T start(Class<T> cls, String url, boolean ssl, boolean trustAllCerts, String trustStore, String trustStorePassword, 
		List<?> providers, int connectTimeout, int receiveTimeout) {

		try {
			
			T resource = JAXRSClientFactory.create(url, cls, providers);
		    HTTPConduit conduit = WebClient.getConfig(resource).getHttpConduit();
		    WebClient.getConfig(resource).getInInterceptors().add(new LoggingInInterceptor());
		    WebClient.getConfig(resource).getOutInterceptors().add(new LoggingOutInterceptor());
			if(ssl) configureHTTPS(resource, conduit, trustAllCerts, trustStore, trustStorePassword);
			
		    HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); 
		    httpClientPolicy.setConnectionTimeout(connectTimeout); 
		    httpClientPolicy.setReceiveTimeout(receiveTimeout); 
		    conduit.setClient(httpClientPolicy);
			
			return resource;
			
		} catch (Exception e) {
			log.error(" rest client '{}': NOT STARTED", url);
			return null;
		}
		
	}
	
	
	public void configureHTTPS(T resource, HTTPConduit conduit, boolean trustAllCerts, String trustStore, String trustStorePassword) {

		if (trustAllCerts) {
		    TLSClientParameters params = conduit.getTlsClientParameters();
		    if (params == null) {
		        params = new TLSClientParameters();
		        conduit.setTlsClientParameters(params);
		    }

			params.setKeyManagers(new KeyManager[0]);
		    params.setTrustManagers(new TrustManager[] { new DumbX509TrustManager() }); 
		    params.setDisableCNCheck(true);
	    	log.info("trust all certs: OK");
		    return;
		} 

		log.info("configuring truststore ...");
		if (StringUtils.trimToNull(trustStore) == null || StringUtils.trimToNull(trustStorePassword) == null) {
			log.info("trustStore or trustStorePassword missing");
			return;
		} 

        try {
            TLSClientParameters tlsParams = new TLSClientParameters();
            tlsParams.setDisableCNCheck(true);
 
            KeyStore keyStore = KeyStore.getInstance("JKS");

			File truststore = new File(trustStore);
            keyStore.load(new FileInputStream(truststore), trustStorePassword.toCharArray());
            TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustFactory.init(keyStore);
            TrustManager[] tm = trustFactory.getTrustManagers();
            tlsParams.setTrustManagers(tm);
 
            truststore = new File(trustStore);
            keyStore.load(new FileInputStream(truststore), trustStorePassword.toCharArray());
            KeyManagerFactory keyFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyFactory.init(keyStore, trustStorePassword.toCharArray());
            KeyManager[] km = keyFactory.getKeyManagers();
            tlsParams.setKeyManagers(km);
 
            FiltersType filter = new FiltersType();
            filter.getInclude().add(".*_EXPORT_.*");
            filter.getInclude().add(".*_EXPORT1024_.*");
            filter.getInclude().add(".*_WITH_DES_.*");
            filter.getInclude().add(".*_WITH_NULL_.*");
            filter.getExclude().add(".*_DH_anon_.*");
            tlsParams.setCipherSuitesFilter(filter);
 
            conduit.setTlsClientParameters(tlsParams);
        	log.info("trustStore: OK");
        }
        catch (Exception e) {
    		log.error("trustStore initialization failed: " + e.getMessage());
        }
	}


}
