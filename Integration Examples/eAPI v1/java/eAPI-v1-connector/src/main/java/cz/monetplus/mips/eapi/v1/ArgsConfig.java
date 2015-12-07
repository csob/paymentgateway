package cz.monetplus.mips.eapi.v1;

import org.kohsuke.args4j.Option;


/**
 * Command line arguments
 */
public class ArgsConfig implements Cloneable {

	public enum RunModeEnum {
		PAYMENT_INIT, 
		PAYMENT_PROCESS, 
		PAYMENT_STATUS, 
		PAYMENT_CLOSE, 
		PAYMENT_REVERSE, 
		PAYMENT_REFUND, 
		ECHO_GET, 
		ECHO_POST, 
		CUSTOMER_INFO
	}
	
	@Option(name = "-m", aliases = { "--mode" }, usage = "Run mode  (default: ECHO_GET)")
	public RunModeEnum runMode = RunModeEnum.ECHO_GET;
	
	@Option(name = "-p", aliases = { "--payId" }, usage = "Pay ID")
	public String payId;

	@Option(name = "-c", aliases = { "--customerId" }, usage = "Customer ID")
	public String customerId;

	@Option(name = "-i", aliases = { "--initFile" }, usage = "Base file for paymentInit JSON request")
	public String initFile;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		ArgsConfig c = new ArgsConfig();
		c.runMode = runMode;
		c.payId = payId;
		c.customerId = customerId;
		c.initFile = initFile;
		return super.clone();
	}
}
