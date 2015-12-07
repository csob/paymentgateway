<?php

class Response {
	var $payId; 
	var $dttm;
	var $resultCode;
	var $resultMessage;
	var $paymentStatus;
	var $authCode;
	var $merchantData;
	var $signature;
}

class DatabaseConfig {
	var $host;
	var $name;
	var $user;
	var $password; 
}

class PaymentStatus {
	static $requested = '1';
	static $pending = '2';
	static $canceled = '3';
	static $approved = '4';
	static $reversed = '5';
	static $declined = '6';
	static $toClearing = '7';
	static $cleared = '8';
	static $refundRequested = '9';
	static $refunded = '10';
}

class NativeApiMethod {
	static $init = "/payment/init/";
	static $process = "/payment/process/";
	static $status = "/payment/status/";
	static $close = "/payment/close/";
	static $reverse = "/payment/reverse/";
	static $refund = "/payment/refund/";
	static $echo = "/echo/";
	static $customerInfo = "/customer/info/";
}

?>