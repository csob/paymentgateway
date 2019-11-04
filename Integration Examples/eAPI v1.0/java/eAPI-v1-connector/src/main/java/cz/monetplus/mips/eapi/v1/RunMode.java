package cz.monetplus.mips.eapi.v1;

import cz.monetplus.mips.eapi.v1.ArgsConfig.RunModeEnum;

public interface RunMode {

	public void proc(ArgsConfig aConfig);
	
	public RunModeEnum getMode();
}
