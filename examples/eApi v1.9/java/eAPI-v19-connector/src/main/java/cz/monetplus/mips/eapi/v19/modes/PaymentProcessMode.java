package cz.monetplus.mips.eapi.v19.modes;

import cz.monetplus.mips.eapi.v19.ArgsConfig;
import cz.monetplus.mips.eapi.v19.RunMode;
import cz.monetplus.mips.eapi.v19.service.ExamplesService;
import cz.monetplus.mips.eapi.v19.service.MipsException;
import cz.monetplus.mips.eapi.v19.service.RespCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.net.URI;

@Slf4j
@Component
public class PaymentProcessMode implements RunMode {
    final
    ExamplesService examplesService;

    public PaymentProcessMode(ExamplesService examplesService) {
        this.examplesService = examplesService;
    }

    @Override
    public void proc(ArgsConfig aConfig) {
        try {
            if (StringUtils.trimToNull(aConfig.payId) == null) {
                throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter payId");
            }
            URI res = examplesService.paymentProcess(aConfig.payId);
            log.info("payment process redirect to : {}", res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArgsConfig.RunModeEnum getMode() {
        return ArgsConfig.RunModeEnum.PAYMENT_PROCESS;
    }
}
