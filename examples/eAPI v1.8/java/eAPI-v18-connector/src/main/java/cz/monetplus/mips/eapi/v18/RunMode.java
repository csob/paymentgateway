package cz.monetplus.mips.eapi.v18;

import cz.monetplus.mips.eapi.v18.ArgsConfig.RunModeEnum;

public interface RunMode {

	void proc(ArgsConfig aConfig);
	
	RunModeEnum getMode();
}
