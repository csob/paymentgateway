package cz.monetplus.mips.eapi.v15.modes;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.monetplus.mips.eapi.v15.ArgsConfig;
import cz.monetplus.mips.eapi.v15.RunMode;
import cz.monetplus.mips.eapi.v15.ArgsConfig.RunModeEnum;
import cz.monetplus.mips.eapi.v15.connector.entity.PayRes;
import cz.monetplus.mips.eapi.v15.service.MipsException;
import cz.monetplus.mips.eapi.v15.service.NativeApiV15Service;
import cz.monetplus.mips.eapi.v15.service.RespCode;

@Component
public class PaymentRefundMode implements RunMode {

	private static final Logger LOG = LoggerFactory.getLogger(PaymentRefundMode.class);
	
	@Autowired
	private NativeApiV15Service nativeApiV1Service;
	
	@Override
	public void proc(ArgsConfig aConfig) {
		
		try {
			if (StringUtils.trimToNull(aConfig.payId) == null) {
				throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter payId");
			}
			if (aConfig.refundAmount != null) {
				LOG.info("using amount for (partial) refund {}", aConfig.refundAmount);
			}
			PayRes res = nativeApiV1Service.paymentRefund(aConfig.payId, aConfig.refundAmount);
			LOG.info("result code: {} [{}], payment status {}", res.resultCode, res.resultMessage, res.paymentStatus);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public RunModeEnum getMode() {
		return RunModeEnum.PAYMENT_REFUND;
	}

}
