package cz.monetplus.mips.eapi.v18.modes;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.monetplus.mips.eapi.v18.ArgsConfig;
import cz.monetplus.mips.eapi.v18.ArgsConfig.RunModeEnum;
import cz.monetplus.mips.eapi.v18.RunMode;
import cz.monetplus.mips.eapi.v18.connector.entity.PayRes;
import cz.monetplus.mips.eapi.v18.service.MipsException;
import cz.monetplus.mips.eapi.v18.service.NativeApiV18Service;
import cz.monetplus.mips.eapi.v18.service.RespCode;

@Component
public class PaymentInitMode implements RunMode {

	private static final Logger LOG = LoggerFactory.getLogger(PaymentInitMode.class);
	
	@Autowired
	private NativeApiV18Service nativeApiService;
	
	@Override
	public void proc(ArgsConfig aConfig) {
		
		try {
			if (StringUtils.trimToNull(aConfig.initFile) == null) {
				throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter initFile for paymentInit operation");
			}
			File f = new File(aConfig.initFile);
			if (!f.isFile() || !f.canRead()) {
				throw new IllegalArgumentException("Unable to load initFile " + f.getAbsolutePath());
			}
			
			PayRes res = nativeApiService.paymentInit(f);
			LOG.info("result code: {} [{}], payment status {}, payId {}", res.resultCode, res.resultMessage, res.paymentStatus, res.payId);
			if (StringUtils.trimToNull(res.customerCode) != null) {
				LOG.info("Custom payment initialized, customer code {}, finish payment via https://(i)platebnibrana.csob.cz/zaplat/{}", res.customerCode, res.customerCode);
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public RunModeEnum getMode() {
		return RunModeEnum.PAYMENT_INIT;
	}

}
