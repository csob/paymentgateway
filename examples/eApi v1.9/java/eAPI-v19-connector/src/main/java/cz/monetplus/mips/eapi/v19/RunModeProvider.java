package cz.monetplus.mips.eapi.v19;

import cz.monetplus.mips.eapi.v19.ArgsConfig.RunModeEnum;

public interface RunModeProvider {
	
	/**
	 * Finds run mode by name
	 * @param mode
	 * @return
	 */
	RunMode find(RunModeEnum mode);

}
