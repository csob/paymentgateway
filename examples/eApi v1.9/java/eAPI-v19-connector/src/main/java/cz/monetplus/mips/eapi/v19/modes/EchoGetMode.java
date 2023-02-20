package cz.monetplus.mips.eapi.v19.modes;

import cz.monetplus.mips.eapi.v19.ArgsConfig;
import cz.monetplus.mips.eapi.v19.ArgsConfig.RunModeEnum;
import cz.monetplus.mips.eapi.v19.RunMode;
import cz.monetplus.mips.eapi.v19.connector.entity.responses.EchoResponse;
import cz.monetplus.mips.eapi.v19.service.ExamplesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EchoGetMode implements RunMode {

	private final ExamplesService examplesService;

	public EchoGetMode(ExamplesService examplesService) {
		this.examplesService = examplesService;
	}

	@Override
	public void proc(ArgsConfig aConfig) {
		
		try {
			EchoResponse res = examplesService.echoGet();
			log.info("result code: {} [{}]", res.getResultCode(), res.getResultMessage());
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public RunModeEnum getMode() {
		return RunModeEnum.ECHO_GET;
	}

}
