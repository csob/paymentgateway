package cz.monetplus.mips.eapi.v1.modes;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.monetplus.mips.eapi.v1.ArgsConfig;
import cz.monetplus.mips.eapi.v1.ArgsConfig.RunModeEnum;
import cz.monetplus.mips.eapi.v1.RunMode;
import cz.monetplus.mips.eapi.v1.connector.entity.PayRes;
import cz.monetplus.mips.eapi.v1.service.MipsException;
import cz.monetplus.mips.eapi.v1.service.NativeApiV1Service;
import cz.monetplus.mips.eapi.v1.service.RespCode;

@Component
public class PaymentStatusMode implements RunMode {

	private static final Logger LOG = LoggerFactory.getLogger(PaymentStatusMode.class);
	
	@Autowired
	private NativeApiV1Service nativeApiV1Service;
	
	@Override
	public void proc(ArgsConfig aConfig) {
		
		try {
			if (StringUtils.trimToNull(aConfig.payId) == null) {
				throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter payId");
			}
			PayRes res = nativeApiV1Service.paymentStatus(aConfig.payId);
			LOG.info("result code: {} [{}], payment status {}", res.resultCode, res.resultMessage, res.paymentStatus);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public RunModeEnum getMode() {
		return RunModeEnum.PAYMENT_STATUS;
	}

}
