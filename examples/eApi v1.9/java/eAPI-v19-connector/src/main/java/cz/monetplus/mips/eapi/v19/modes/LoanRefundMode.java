package cz.monetplus.mips.eapi.v19.modes;

import cz.monetplus.mips.eapi.v19.ArgsConfig;
import cz.monetplus.mips.eapi.v19.RunMode;
import cz.monetplus.mips.eapi.v19.connector.entity.responses.PaymentResponse;
import cz.monetplus.mips.eapi.v19.service.ExamplesService;
import cz.monetplus.mips.eapi.v19.service.MipsException;
import cz.monetplus.mips.eapi.v19.service.RespCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class LoanRefundMode implements RunMode {
    private final ExamplesService examplesService;

    public LoanRefundMode(ExamplesService examplesService) {
        this.examplesService = examplesService;
    }

    @Override
    public void proc(ArgsConfig aConfig) {
        try {
            if (StringUtils.trimToNull(aConfig.initFile) == null) {
                throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter initFile");
            }
            File f = new File(aConfig.initFile);
            if (!f.isFile() || !f.canRead()) {
                throw new IllegalArgumentException("Unable to load initFile " + f.getAbsolutePath());
            }

            PaymentResponse res = examplesService.loanRefund(f);
            log.info("result code: {} [{}], payment status {}, payId {}", res.getResultCode(), res.getResultMessage(), res.getPaymentStatus(), res.getPayId());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArgsConfig.RunModeEnum getMode() {
        return ArgsConfig.RunModeEnum.LOAN_REFUND;
    }
}
