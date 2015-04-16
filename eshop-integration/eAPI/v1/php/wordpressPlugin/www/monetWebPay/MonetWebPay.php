<?php
require_once(dirname(__FILE__)."/crypto.php");
require_once(dirname(__FILE__)."/db.php");
require_once(dirname(__FILE__)."/logger.php");
require_once(dirname(__FILE__)."/struct.php");

class MonetWebPay {
	var $log;
	public function __construct($databaseConfig) {
		global $mysql;
		$this->log = new Logger();
		$mysql = array(
				'host' => $databaseConfig -> host,
				'name' => $databaseConfig -> name,
				'user' => $databaseConfig -> user,
				'password' => $databaseConfig -> password
		);
	}
	
	function verifyPaymentInitSignature($response, $publicKey, $logMsg) {
		return verifyResponse($response, $publicKey, $logMsg);
	}

	function verifyPaymentResponseSignature($response, $publicKey, $logMsg) {
		return verifyResponse($response, $publicKey, $logMsg);
	}

	function insertTransaction($response, $orderNumber, $cart) {
		sqlconnect();
		$sql = "insert into monetTransaction(orderNumber, payId, paymentStatus, created, updated, paymentResponse, authCode, cart) values (" 
				. toSql($orderNumber) . ", "
				. toSql($response->payId) .", "
				. toSql($response->paymentStatus) .", "
				. "NOW()" .", "
				. toSql(null) .", "
				. toSql(strtotime($response->dttm)) .", "
				. toSql(null) . ", "
				. toSql(json_encode($cart)) . ")";
		$this->log->write($sql);
		sqlExecute($sql);
	}
	
	function updateTransaction($orderNumber, $response, $cart) {
		sqlconnect();
		$sql = "update monetTransaction set cart = " . toSql(json_encode($cart)) . ", authCode = " . toSql($response -> authCode) .", paymentStatus=" . toSql($response->paymentStatus) . ", updated=" . "NOW()" . ", paymentResponse=" . toSql(strtotime($response->dttm)) . " where orderNumber = " . toSql($orderNumber);
		$this->log->write($sql);
		sqlExecute($sql);		
	}

	function updateTransactionStatus($orderNumber, $response) {
		sqlconnect();
		$sql = "update monetTransaction set authCode = " . toSql($response -> authCode) .", paymentStatus=" . toSql($response->paymentStatus) . ", updated=" . "NOW()" . ", paymentResponse=" . toSql(strtotime($response->dttm)) . " where orderNumber = " . toSql($orderNumber);
		$this->log->write($sql);
		sqlExecute($sql);		
	}
	
	function selectTransaction($orderNumber) {
		sqlconnect();
		$sql = "select * from monetTransaction where orderNumber=" . $orderNumber;
		$result = sqlExecute($sql);
		$row = mysql_fetch_assoc($result);
		$this->log->write($sql);
		return $row;
	}
	
	function clearTransaction($orderNumber, $response) {
		sqlconnect();
		$sql = "update monetTransaction set paymentStatus = " .  toSql($response->paymentStatus) . ", authCode = " . toSql(null) .", updated=NOW(), payId=" . toSql(null) . " where orderNumber = " . toSql($orderNumber);
		$this->log->write($sql);
		sqlExecute($sql);
	}
}

?>