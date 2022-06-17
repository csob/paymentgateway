package cz.monetplus.mips.eapi.v19.modes;

import cz.monetplus.mips.eapi.v19.ArgsConfig;
import cz.monetplus.mips.eapi.v19.ArgsConfig.RunModeEnum;
import cz.monetplus.mips.eapi.v19.RunMode;
import cz.monetplus.mips.eapi.v19.connector.entity.responses.PaymentResponse;
import cz.monetplus.mips.eapi.v19.service.ExamplesService;
import cz.monetplus.mips.eapi.v19.service.MipsException;
import cz.monetplus.mips.eapi.v19.service.RespCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MallpayCancelMode implements RunMode {
    private final ExamplesService examplesService;

    public MallpayCancelMode(ExamplesService examplesService) {
        this.examplesService = examplesService;
    }

    @Override
    public void proc(ArgsConfig aConfig) {
        try {
			if (StringUtils.trimToNull(aConfig.payId) == null) {
				throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter payId");
			}
            if (StringUtils.trimToNull(aConfig.cancelReason) == null) {
                throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter reason");
            }

			PaymentResponse res = examplesService.mallpayCancel(aConfig.payId, aConfig.cancelReason);
			log.info("result code: {} [{}], payment status {}, payId {}", res.getResultCode(), res.getResultMessage(), res.getPaymentStatus(), res.getPayId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
    }

    @Override
    public RunModeEnum getMode() {
        return RunModeEnum.MALLPAY_CANCEL;
    }
    
}
