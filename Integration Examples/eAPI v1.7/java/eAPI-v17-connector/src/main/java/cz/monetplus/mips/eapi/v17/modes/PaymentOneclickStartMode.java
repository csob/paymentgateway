package cz.monetplus.mips.eapi.v17.modes;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.monetplus.mips.eapi.v17.ArgsConfig;
import cz.monetplus.mips.eapi.v17.RunMode;
import cz.monetplus.mips.eapi.v17.ArgsConfig.RunModeEnum;
import cz.monetplus.mips.eapi.v17.connector.entity.PayRes;
import cz.monetplus.mips.eapi.v17.service.MipsException;
import cz.monetplus.mips.eapi.v17.service.NativeApiV17Service;
import cz.monetplus.mips.eapi.v17.service.RespCode;

@Component
public class PaymentOneclickStartMode implements RunMode {

	private static final Logger LOG = LoggerFactory.getLogger(PaymentOneclickStartMode.class);
	
	@Autowired
	private NativeApiV17Service nativeApiV17Service;
	
	@Override
	public void proc(ArgsConfig aConfig) {
		
		try {
			if (StringUtils.trimToNull(aConfig.payId) == null) {
				throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter payId");
			}
			PayRes res = nativeApiV17Service.paymentOneclickStart(aConfig.payId);
			LOG.info("result code: {} [{}], payment status {}", res.resultCode, res.resultMessage, res.paymentStatus);
			if (res.paymentStatus != null && res.paymentStatus == 2) {
				LOG.info("oneclick payment authorization is in pending state, call payment/status to check trx state");
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public RunModeEnum getMode() {
		return RunModeEnum.PAYMENT_ONECLICK_START;
	}

}
