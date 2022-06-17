package cz.monetplus.mips.eapi.v19;

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
		ONECLICK_PROCESS,
		ECHO_GET, 
		ECHO_POST,
		APPLEPAY_ECHO,
		APPLEPAY_INIT,
		APPLEPAY_PROCESS,
		GOOGLEPAY_ECHO,
		GOOGLEPAY_INIT,
		GOOGLEPAY_PROCESS,
		BUTTON_INIT,
		MALLPAY_INIT,
		MALLPAY_CANCEL,
		MALLPAY_LOGISTICS,
		MALLPAY_REFUND,
		LOAN_INIT,
		LOAN_CANCEL,
		LOAN_LOGISTICS,
		LOAN_REFUND,
		ECHO_CUSTOMER
	}
	
	@Option(name = "-m", aliases = { "--mode" }, usage = "Run mode  (default: ECHO_GET)")
	public RunModeEnum runMode = RunModeEnum.ECHO_GET;
	
	@Option(name = "-p", aliases = { "--payId" }, usage = "Pay ID")
	public String payId;

	@Option(name = "-c", aliases = { "--customerId" }, usage = "Customer ID")
	public String customerId;

	@Option(name = "-i", aliases = { "--initFile" }, usage = "Base file for init operations JSON requests")
	public String initFile;

	@Option(name = "-a", aliases = { "--amount" }, usage = "(partial) amount for payment/refund operation or totalAmount for payment/close operation")
	public Long amount;

	@Option(name = "-x", aliases = { "--origPayId" }, usage = "origPayId for oneclick/echo")
	public String origPayId;

	@Option(name = "-t", aliases = { "--payload" }, usage = "payload token for applepay and googlepay init methods")
	public String payload;

	@Option(name = "-r", aliases = { "--reason" }, usage = "reason for mallpay and loan operations")
	public String cancelReason;

	@Override
	protected Object clone() throws CloneNotSupportedException {
		ArgsConfig c = new ArgsConfig();
		c.runMode = runMode;
		c.payId = payId;
		c.customerId = customerId;
		c.initFile = initFile;
		c.amount = amount;
		c.origPayId = origPayId;
		c.payload = payload;
		c.cancelReason = cancelReason;
		return super.clone();
	}
}
