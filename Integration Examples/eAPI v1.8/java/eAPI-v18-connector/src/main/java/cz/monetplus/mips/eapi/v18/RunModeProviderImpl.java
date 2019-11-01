package cz.monetplus.mips.eapi.v18;

import java.util.EnumMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import cz.monetplus.mips.eapi.v18.ArgsConfig.RunModeEnum;

@Component
public class RunModeProviderImpl implements BeanPostProcessor, RunModeProvider {

	private static final Logger LOG = LoggerFactory.getLogger(RunModeProviderImpl.class);

	final EnumMap<RunModeEnum, RunMode> runModes = new EnumMap<ArgsConfig.RunModeEnum, RunMode>(RunModeEnum.class);
	
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
				LOG.debug("Registered mode {}",runMode.getMode());
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
