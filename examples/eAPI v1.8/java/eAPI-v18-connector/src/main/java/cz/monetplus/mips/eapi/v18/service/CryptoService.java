package cz.monetplus.mips.eapi.v18.service;

import cz.monetplus.mips.eapi.v18.connector.entity.SignBase;


public interface CryptoService {

	/**
	 * 
	 * @param signBase
	 * @return
	 */
	boolean isSignatureValid(SignBase signBase);

	/**
	 * 
	 * @param signBase
	 * @throws MipsException
	 */
	void createSignature(SignBase signBase) throws MipsException;
	
}
