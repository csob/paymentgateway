package cz.monetplus.mips.eapi.v15.modes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.monetplus.mips.eapi.v15.ArgsConfig;
import cz.monetplus.mips.eapi.v15.RunMode;
import cz.monetplus.mips.eapi.v15.ArgsConfig.RunModeEnum;
import cz.monetplus.mips.eapi.v15.connector.entity.EchoRes;
import cz.monetplus.mips.eapi.v15.service.NativeApiV15Service;

@Component
public class EchoGetMode implements RunMode {

	private static final Logger LOG = LoggerFactory.getLogger(EchoGetMode.class);
	
	@Autowired
	private NativeApiV15Service nativeApiV1Service;
	
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
