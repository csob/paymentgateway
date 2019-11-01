package cz.monetplus.mips.eapi.v18.service;

import java.io.File;

import cz.monetplus.mips.eapi.v18.connector.entity.CustRes;
import cz.monetplus.mips.eapi.v18.connector.entity.EchoRes;
import cz.monetplus.mips.eapi.v18.connector.entity.OneclickEchoRes;
import cz.monetplus.mips.eapi.v18.connector.entity.PayRes;


public interface NativeApiV18Service {

	/**
	 * @param initFile
	 * @return  
	 */
	PayRes paymentInit(File initFile) throws MipsException;

	/**
	 * @param payId payment ID
	 * @return final URL to open payment in web browser 
	 */
	String paymentProcess(String payId) throws MipsException;

	/**
	 * @param payId payment ID
	 * @return
	 */
	PayRes paymentStatus(String payId) throws MipsException;

	/**
	 * @param payId payment ID
	 * @return
	 */
	PayRes paymentClose(String payId) throws MipsException;

	/**
	 * @param payId payment ID
	 * @return
	 */
	PayRes paymentReverse(String payId) throws MipsException;

	/**
	 * @param payId payment ID
	 * @return
	 */
	PayRes paymentRefund(String payId, Long amount) throws MipsException;

	/**
	 * @return
	 */
	EchoRes echoGet() throws MipsException;
	
	/**
	 * @return
	 */
	EchoRes echoPost() throws MipsException;

	/**
	 * @param customerId customer ID
	 * @return
	 */
	CustRes echoCustomer(String customerId) throws MipsException;

	/**
	 * @param origPayId
	 * @return  
	 */
	OneclickEchoRes oneclickEcho(String origPayId) throws MipsException;

	/**
	 * @param oneclickFile
	 * @return  
	 */
	PayRes oneclickInit(File oneclickFile) throws MipsException;

	/**
	 * @param payId
	 * @return  
	 */
	PayRes oneclickStart(String payId) throws MipsException;

}
