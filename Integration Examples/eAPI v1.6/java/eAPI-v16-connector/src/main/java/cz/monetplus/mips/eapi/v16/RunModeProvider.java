package cz.monetplus.mips.eapi.v16;

import cz.monetplus.mips.eapi.v16.ArgsConfig.RunModeEnum;

public interface RunModeProvider {
	
	/**
	 * Finds run mode by name
	 * @param mode
	 * @return
	 */
	public RunMode find(RunModeEnum mode);

}
