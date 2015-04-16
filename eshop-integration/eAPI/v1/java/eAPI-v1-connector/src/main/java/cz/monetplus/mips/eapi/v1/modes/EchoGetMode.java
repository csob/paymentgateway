package cz.monetplus.mips.eapi.v1.modes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.monetplus.mips.eapi.v1.ArgsConfig;
import cz.monetplus.mips.eapi.v1.ArgsConfig.RunModeEnum;
import cz.monetplus.mips.eapi.v1.RunMode;
import cz.monetplus.mips.eapi.v1.connector.entity.EchoRes;
import cz.monetplus.mips.eapi.v1.service.NativeApiV1Service;

@Component
public class EchoGetMode implements RunMode {

	private static final Logger LOG = LoggerFactory.getLogger(EchoGetMode.class);
	
	@Autowired
	private NativeApiV1Service nativeApiV1Service;
	
	@Override
	public void proc(ArgsConfig aConfig) {
		
		try {
			EchoRes res = nativeApiV1Service.echoGet();
			LOG.info("result code: {} [{}]", res.resultCode, res.resultMessage);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public RunModeEnum getMode() {
		return RunModeEnum.ECHO_GET;
	}

}
