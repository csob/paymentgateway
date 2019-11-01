package cz.monetplus.mips.eapi.v18;

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
		ONECLICK_ECHO, 
		ONECLICK_INIT, 
		ONECLICK_START, 
		ECHO_GET, 
		ECHO_POST, 
		ECHO_CUSTOMER
	}
	
	@Option(name = "-m", aliases = { "--mode" }, usage = "Run mode  (default: ECHO_GET)")
	public RunModeEnum runMode = RunModeEnum.ECHO_GET;
	
	@Option(name = "-p", aliases = { "--payId" }, usage = "Pay ID")
	public String payId;

	@Option(name = "-c", aliases = { "--customerId" }, usage = "Customer ID")
	public String customerId;

	@Option(name = "-i", aliases = { "--initFile" }, usage = "Base file for paymentInit JSON request")
	public String initFile;

	@Option(name = "-o", aliases = { "--oneclickFile" }, usage = "Base file for paymentOneclickInit JSON request")
	public String oneclickFile;

	@Option(name = "-a", aliases = { "--refundAmount" }, usage = "(partial) amount for payment/refund operation")
	public Long refundAmount;

	@Option(name = "-x", aliases = { "--origPayId" }, usage = "origPayId for oneclick/echo")
	public String origPayId;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		ArgsConfig c = new ArgsConfig();
		c.runMode = runMode;
		c.payId = payId;
		c.customerId = customerId;
		c.initFile = initFile;
		c.oneclickFile = oneclickFile;
		c.refundAmount = refundAmount;
		c.origPayId = origPayId;
		return super.clone();
	}
}
