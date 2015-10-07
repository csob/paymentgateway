package cz.monetplus.mips.eapi.v15;

import cz.monetplus.mips.eapi.v15.ArgsConfig.RunModeEnum;

public interface RunModeProvider {
	
	/**
	 * Finds run mode by name
	 * @param mode
	 * @return
	 */
	public RunMode find(RunModeEnum mode);

}
