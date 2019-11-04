<?php
require_once ('logger.php');
require_once ('struct.php');


/**
 * Creates cart with exact two items (goods, postage)
 * @param goodsDesc description of goods
 * @param totalAmount total order amount in CZK
 * @param shippingTotal shipping amount in CZK
 */
function createCartData($goodsDesc, $totalAmount, $shippingTotal) {
	$goodsDesc = mb_substr(trim($goodsDesc), 0, 37, 'utf-8') . "...";
	$totalAmount = (int) ($totalAmount * 100);
	$shippingTotal = (int) ($shippingTotal * 100);

	$cartData = array(
			0 => array(
				"name" 			=> 	Constants::$SHOP_CART_NAME,
				"quantity"		=>	Constants::$SHOP_CART_QUANTITY,
				"amount"		=>	$totalAmount - $shippingTotal,
				"description" 	=>	$goodsDesc
			),
			1 => array(
				"name" 		=>  Constants::$SHOP_SHIPPING,
				"quantity" 	=>	Constants::$SHOP_CART_QUANTITY,
				"amount"	=>	$shippingTotal,
				"description" 	=>	Constants::$SHOP_SHIPPING_DESC
			)
	);
	return $cartData;
}

/**
 * Creates JSON data for payment/init including signature
 * @param merchantId ID of merchant assigned by bank
 * @param orderNo reference order number
 * @param dttm date and time ot request sending, format YYYYMMDDHHMMSS
 * @param totalAmount total order amount
 * @param returnUrl return URL address for back redirecting from payment gateway to e-shop
 * @param cart cart structured data
 * @param description brief payment description
 * @param customerId customer ID
 * @param privateKey merchant private key for data signing
 * @param privateKeyPassword merchant private key password
 * @param closePayment close payment flag (0/1)
 * @param merchantData merchant base64 encoded data
 * @param returnMethodPOST indicator if return method POST should be used
 */
function createPaymentInitData( $merchantId, $orderNo, $dttm, $totalAmount, $returnUrl, $cart, $description,
	$customerId, $privateKey, $privateKeyPassword, $closePayment, $merchantData, $returnMethodPOST) {

	$payOperation = Constants::$PAYOPERATION;
	$payMethod = Constants::$PAYMETHOD;
	$currency = Constants::$CURRENCY;

	$returnMethod = ($returnMethodPOST == 'yes') ? Constants::$POST_RETURNMETHOD : Constants::$GET_RETURNMETHOD;
	$closePayment = ($closePayment == '1') ? "true" : "false";
	$totalAmount = (int) ($totalAmount * 100);

	$data = array (
			"merchantId"	=>	$merchantId,
			"orderNo" 		=>	$orderNo,
			"dttm"			=>	$dttm,
			"payOperation"	=>	$payOperation,
			"payMethod"		=> 	$payMethod,
			"totalAmount"	=>	$totalAmount,
			"currency"		=>	$currency,
			"closePayment"	=>	$closePayment,
			"returnUrl"		=>	$returnUrl,
			"returnMethod"	=>	$returnMethod,
			"cart"			=>	$cart,
			"description"	=>	$description,
			"merchantData"	=>	$merchantData
	);
	if(!is_null($customerId) && $customerId != '0') {
		$data["customerId"]	= $customerId;
	}

	$data["language"] = Constants::$LANGUAGE;
	$data["signature"]=	signPaymentInitData ($data, $privateKey, $privateKeyPassword);

 	return $data;
}

function signPaymentInitData( $data, $privateKey, $privateKeyPassword) {

	$cart2Sign = $data["cart"][0]["name"] . "|" . $data["cart"][0]["quantity"] . "|" . $data["cart"][0]["amount"] . "|" . $data["cart"][0]["description"] . "|"
		. $data["cart"][1]["name"] . "|" . $data["cart"][1]["quantity"] . "|" . $data["cart"][1]["amount"] . "|" . $data["cart"][1]["description"];

	$data2Sign = $data["merchantId"] . "|" .  $data["orderNo"] . "|" . $data["dttm"] . "|" . $data["payOperation"] . "|" . $data["payMethod"] . "|" . $data["totalAmount"]
		."|". $data["currency"] ."|". $data["closePayment"]  . "|". $data["returnUrl"] ."|". $data["returnMethod"] . "|" . $cart2Sign . "|" . $data["description"];

	$merchantData = $data["merchantData"];
	if(!is_null($merchantData)) {
		$data2Sign = $data2Sign . "|" . $merchantData;
	}

	$customerId = $data["customerId"];
	if(!is_null($customerId) && $customerId != '0') {
		$data2Sign = $data2Sign . "|" . $customerId;
	}

	$data2Sign = $data2Sign . "|" . $data["language"];

	if ($data2Sign [strlen ( $data2Sign ) - 1] == '|') {
		$data2Sign = substr ( $data2Sign, 0, strlen ( $data2Sign ) - 1 );
	}

	echo "data to sign:\n\"" . htmlspecialchars($data2Sign, ENT_QUOTES) . "\"\n\n";

	return sign ( $data2Sign, $privateKey, $privateKeyPassword, "payment/init data to sign:");

}


function createGetParams($merchantId, $payId, $dttm, $privateKey, $privateKeyPassword) {
	$text =  $merchantId . "|" . $payId . "|" . $dttm;
	$signature = sign($text, $privateKey, $privateKeyPassword, "data to sign:");
	return $merchantId . "/" . $payId . "/" . $dttm . "/" . urlencode($signature);
}

function preparePutRequest($merchantId, $payId, $dttm, $privateKey, $privateKeyPassword) {
	$data = array (
		"merchantId"	=>	$merchantId,
		"payId" 		=>	$payId,
		"dttm"			=>	$dttm
	);
	$text =  $merchantId . "|" . $payId . "|" . $dttm;
	$data['signature'] = sign($text, $privateKey, $privateKeyPassword, "data to sign:");
	return $data;
}



function verifyResponse($response, $key, $logMsg) {
	$text = $response ['payId'] . "|" . $response ['dttm'] . "|" . $response ['resultCode'] . "|" . $response ['resultMessage'];

	if(!is_null($response ['paymentStatus'])) {
		$text = $text  . "|" . $response ['paymentStatus'];
	}

	if(isset($response ['authCode']) && !is_null($response ['authCode'])) {
		$text = $text  . "|" . $response ['authCode'];
	}

	if(isset($response ['merchantData']) && !is_null($response ['merchantData'])) {
		$text = $text  . "|" . $response ['merchantData'];
	}

	return verify($text, $response ['signature'], $key, $logMsg);
}


function sign($text, $key, $passwd, $logMsg = null) {
	$logger = new Logger();
	$logger->write($logMsg . ": '" . $text . "'");
	$path = dirname(__FILE__)."/keys/";
	$key = $path . $key;
	$fp = fopen ( $key, "r" );
	if (! $fp) {
		throw new Exception ( "Key not found" );
	}
	$private = fread ( $fp, filesize ( $key ) );
	fclose ( $fp );
	$privateKeyId = openssl_get_privatekey ( $private, $passwd );
	openssl_sign ( $text, $signature, $privateKeyId );
	$signature = base64_encode ( $signature );
	openssl_free_key ( $privateKeyId );
	return $signature;
}

function verify($text, $signatureBase64, $key, $logMsg = null) {
	$logger = new Logger();
	$logger->write($logMsg . ": " . $text);
	$path = dirname(__FILE__)."/keys/";
	$key = $path . $key;
	$fp = fopen ( $key, "r" );
	if (! $fp) {
		throw new Exception ( "Key not found" );
	}
	$public = fread ( $fp, filesize ( $key ) );
	fclose ( $fp );
	$publicKeyId = openssl_get_publickey ( $public );
	$signature = base64_decode ( $signatureBase64 );
	$res = openssl_verify ( $text, $signature, $publicKeyId );
	openssl_free_key ( $publicKeyId );
	return (($res != '1') ? false : true);
}
?>