package cz.monetplus.mips.eapi.v17;

import cz.monetplus.mips.eapi.v17.ArgsConfig.RunModeEnum;

public interface RunMode {

	public void proc(ArgsConfig aConfig);
	
	public RunModeEnum getMode();
}
