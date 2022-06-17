package cz.monetplus.mips.eapi.v19;

import cz.monetplus.mips.eapi.v19.ArgsConfig.RunModeEnum;

public interface RunMode {

	void proc(ArgsConfig aConfig);
	
	RunModeEnum getMode();
}
