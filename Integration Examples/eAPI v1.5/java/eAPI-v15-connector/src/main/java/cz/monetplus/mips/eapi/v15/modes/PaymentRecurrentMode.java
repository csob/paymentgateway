package cz.monetplus.mips.eapi.v15.modes;

import java.io.File;

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
public class PaymentRecurrentMode implements RunMode {

	private static final Logger LOG = LoggerFactory.getLogger(PaymentRecurrentMode.class);
	
	@Autowired
	private NativeApiV15Service nativeApiV1Service;
	
	@Override
	public void proc(ArgsConfig aConfig) {
		
		try {
			if (StringUtils.trimToNull(aConfig.recurrentFile) == null) {
				throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter recurrentFile for paymentRecurrent operation");
			}
			File f = new File(aConfig.recurrentFile);
			if (!f.isFile() || !f.canRead()) {
				throw new IllegalArgumentException("Unable to load recurrentFile " + f.getAbsolutePath());
			}
			
			PayRes res = nativeApiV1Service.paymentRecurrent(f);
			LOG.info("result code: {} [{}], payment status {}, payId {}", res.resultCode, res.resultMessage, res.paymentStatus, res.payId);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public RunModeEnum getMode() {
		return RunModeEnum.PAYMENT_RECURRENT;
	}

}
