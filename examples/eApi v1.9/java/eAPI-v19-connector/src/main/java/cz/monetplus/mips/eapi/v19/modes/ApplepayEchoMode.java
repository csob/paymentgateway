package cz.monetplus.mips.eapi.v19.modes;

import cz.monetplus.mips.eapi.v19.ArgsConfig;
import cz.monetplus.mips.eapi.v19.RunMode;
import cz.monetplus.mips.eapi.v19.connector.entity.responses.ApplepayEchoResponse;
import cz.monetplus.mips.eapi.v19.service.ExamplesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplepayEchoMode implements RunMode {
    private final ExamplesService examplesService;

    public ApplepayEchoMode(ExamplesService examplesService) {
        this.examplesService = examplesService;
    }

    @Override
    public void proc(ArgsConfig aConfig) {
        try {
            ApplepayEchoResponse res = examplesService.applepayEcho();
            log.info("result code: {} [{}]", res.getResultCode(), res.getResultMessage());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArgsConfig.RunModeEnum getMode() {
        return ArgsConfig.RunModeEnum.APPLEPAY_ECHO;
    }
}
