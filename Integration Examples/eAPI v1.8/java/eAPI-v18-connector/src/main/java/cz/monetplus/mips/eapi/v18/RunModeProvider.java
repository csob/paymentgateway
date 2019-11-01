package cz.monetplus.mips.eapi.v18;

import cz.monetplus.mips.eapi.v18.ArgsConfig.RunModeEnum;

public interface RunModeProvider {
	
	/**
	 * Finds run mode by name
	 * @param mode
	 * @return
	 */
	public RunMode find(RunModeEnum mode);

}
