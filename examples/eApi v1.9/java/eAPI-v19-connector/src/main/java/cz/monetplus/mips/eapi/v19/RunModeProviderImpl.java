package cz.monetplus.mips.eapi.v19;

import cz.monetplus.mips.eapi.v19.ArgsConfig.RunModeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

@Component
@Slf4j
public class RunModeProviderImpl implements BeanPostProcessor, RunModeProvider {

	final EnumMap<RunModeEnum, RunMode> runModes = new EnumMap<>(RunModeEnum.class);
	
	@Override
	public RunMode find(RunModeEnum mode) {
		if (runModes.containsKey(mode)) {
			return runModes.get(mode);
		}
		return null;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (RunMode.class.isAssignableFrom(bean.getClass())) {
			RunMode runMode = (RunMode) bean;
			
			if (!runModes.containsKey(runMode.getMode())) {
				log.debug("Registered mode {}",runMode.getMode());
				runModes.put(runMode.getMode(), runMode);
			}
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
