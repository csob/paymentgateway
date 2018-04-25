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
import cz.monetplus.mips.eapi.v17.connector.entity.ext.Extension;
import cz.monetplus.mips.eapi.v17.connector.entity.ext.TrxDatesExtension;
import cz.monetplus.mips.eapi.v17.service.MipsException;
import cz.monetplus.mips.eapi.v17.service.NativeApiV17Service;
import cz.monetplus.mips.eapi.v17.service.RespCode;

@Component
public class PaymentStatusMode implements RunMode {

	private static final Logger LOG = LoggerFactory.getLogger(PaymentStatusMode.class);
	
	@Autowired
	private NativeApiV17Service nativeApiV17Service;
	
	@Override
	public void proc(ArgsConfig aConfig) {
		
		try {
			if (StringUtils.trimToNull(aConfig.payId) == null) {
				throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter payId");
			}
			PayRes res = nativeApiV17Service.paymentStatus(aConfig.payId);
			LOG.info("result code: {} [{}], payment status {}", res.resultCode, res.resultMessage, res.paymentStatus);
			for (Extension ext : res.extensions) {
				if (ext instanceof TrxDatesExtension) {
					TrxDatesExtension trxExt = (TrxDatesExtension) ext;
					LOG.info("extension: {}, createdDate: {}, authDate: {}, settlementDate: {}", trxExt.extension, trxExt.createdDate, trxExt.authDate, trxExt.settlementDate);
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public RunModeEnum getMode() {
		return RunModeEnum.PAYMENT_STATUS;
	}

}
