package cz.monetplus.mips.eapi.v16;

import cz.monetplus.mips.eapi.v16.ArgsConfig.RunModeEnum;

public interface RunMode {

	public void proc(ArgsConfig aConfig);
	
	public RunModeEnum getMode();
}
