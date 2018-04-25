package cz.monetplus.mips.eapi.v17.modes;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.monetplus.mips.eapi.v17.ArgsConfig;
import cz.monetplus.mips.eapi.v17.RunMode;
import cz.monetplus.mips.eapi.v17.ArgsConfig.RunModeEnum;
import cz.monetplus.mips.eapi.v17.connector.entity.CustRes;
import cz.monetplus.mips.eapi.v17.service.MipsException;
import cz.monetplus.mips.eapi.v17.service.NativeApiV17Service;
import cz.monetplus.mips.eapi.v17.service.RespCode;

@Component
public class CustomerInfoMode implements RunMode {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerInfoMode.class);
	
	@Autowired
	private NativeApiV17Service nativeApiV17Service;
	
	@Override
	public void proc(ArgsConfig aConfig) {
		
		try {
			if (StringUtils.trimToNull(aConfig.customerId) == null) {
				throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter customerId");
			}
			CustRes res = nativeApiV17Service.customerInfo(aConfig.customerId);
			LOG.info("result code: {} [{}]", res.resultCode, res.resultMessage);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public RunModeEnum getMode() {
		return RunModeEnum.CUSTOMER_INFO;
	}

}
