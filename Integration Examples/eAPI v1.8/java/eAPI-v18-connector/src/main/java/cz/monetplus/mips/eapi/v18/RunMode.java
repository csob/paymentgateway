package cz.monetplus.mips.eapi.v18;

import cz.monetplus.mips.eapi.v18.ArgsConfig.RunModeEnum;

public interface RunMode {

	public void proc(ArgsConfig aConfig);
	
	public RunModeEnum getMode();
}
