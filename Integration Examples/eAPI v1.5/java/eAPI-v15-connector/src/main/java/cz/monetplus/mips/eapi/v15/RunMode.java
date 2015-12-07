package cz.monetplus.mips.eapi.v15;

import cz.monetplus.mips.eapi.v15.ArgsConfig.RunModeEnum;

public interface RunMode {

	public void proc(ArgsConfig aConfig);
	
	public RunModeEnum getMode();
}
