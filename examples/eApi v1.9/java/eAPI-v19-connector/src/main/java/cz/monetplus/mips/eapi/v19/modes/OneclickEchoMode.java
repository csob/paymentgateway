package cz.monetplus.mips.eapi.v19.modes;

import cz.monetplus.mips.eapi.v19.ArgsConfig;
import cz.monetplus.mips.eapi.v19.RunMode;
import cz.monetplus.mips.eapi.v19.connector.entity.responses.OneclickEchoResponse;
import cz.monetplus.mips.eapi.v19.service.ExamplesService;
import cz.monetplus.mips.eapi.v19.service.MipsException;
import cz.monetplus.mips.eapi.v19.service.RespCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OneclickEchoMode implements RunMode {
    private final ExamplesService examplesService;

    public OneclickEchoMode(ExamplesService examplesService) {
        this.examplesService = examplesService;
    }

    @Override
    public void proc(ArgsConfig aConfig) {
        try {
            if (StringUtils.trimToNull(aConfig.origPayId) == null) {
                throw new MipsException(RespCode.INVALID_PARAM, "Missing mandatory parameter origPayId ");
            }
            OneclickEchoResponse res = examplesService.oneclickEcho(aConfig.origPayId);
            log.info("result code: {} [{}]", res.getResultCode(), res.getResultMessage());

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArgsConfig.RunModeEnum getMode() {
        return ArgsConfig.RunModeEnum.ONECLICK_ECHO;
    }
}
