<?php
require_once ('logger.php');

class Constants {
	static $SHOP_CART_QUANTITY = 1;
	static $PAYMETHOD = 'card';
	static $PAYOPERATION = 'payment';
	static $GET_RETURNMETHOD = 'GET';
	static $POST_RETURNMETHOD = 'POST';
}

/**
 * Creates cart with exact two items (goods, postage)
 * @param cart woo commerce cart structure
 * @param totalAmount total order amount in CZK
 */
function createCartData($cart, $totalAmount, $firstCartItemDesc, $secondCartItemDesc) {
	$titles;
	// extract the titles of products for description
	foreach ($cart->get_cart() as $item => $values) { 
		$product = $values['data'];
		$titles = ' ' . $product -> get_title();					
	}
	$titles = mb_substr(trim($titles), 0, 37, 'utf-8') . "...";;
	$totalAmount = $totalAmount * 100;

	// extract shippingTotal from string <span class="amount">99&nbsp;Kc</span>
	$shippingTotal = substr($cart -> get_cart_shipping_total(), 21, 2);
	$shippingTotal = $shippingTotal * 100;

	$cartData = array(
			0 => array(
				"name" 			=> 	$firstCartItemDesc,
				"quantity"		=>	Constants::$SHOP_CART_QUANTITY,
				"amount"		=>	$totalAmount - $shippingTotal,
				"description" 	=>	$titles
			),
			1 => array(
				"name" 		=>  $secondCartItemDesc,
				"quantity" 	=>	Constants::$SHOP_CART_QUANTITY,
				"amount"	=>	$shippingTotal
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
 * @param currency currency in which the order is being made
 * @param language language of the payment interface
 */
function createPaymentInitData( $merchantId, $orderNo, $dttm, $totalAmount, $returnUrl, $cart, $description, 
	$customerId, $privateKey, $privateKeyPassword, $closePayment, $merchantData, $returnMethodPOST, $currency = "CZK", $language = "CS") { 
	
	$payOperation = Constants::$PAYOPERATION;
	$payMethod = Constants::$PAYMETHOD;

	$returnMethod = ($returnMethodPOST == 'yes') ? Constants::$POST_RETURNMETHOD : Constants::$GET_RETURNMETHOD;
	$closePayment = ($closePayment == '1') ? "true" : "false";
	$totalAmount = $totalAmount * 100;
	$titles = $cart[0]['description'];
	$shippingTotal = $cart[1]['amount'];

	$supportedLanguages = ['CZ', 'EN', 'DE', 'SK'];

	if ($language == 'CS') { // ČSOB chybně používá kódy zemí místo kódu jazyka (ISO 639-1)
		$language = 'CZ';
	}

	if (!in_array($language, $supportedLanguages)) {
		$language = 'EN';
	}
	
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
			"merchantData"	=>	$merchantData,
			"language"		=>	$language
	);
	if(!is_null($customerId) && $customerId != '0') {
		$data["customerId"]	= $customerId;
	}

	$data["signature"]=	signPaymentInitData ($data, $privateKey, $privateKeyPassword);

 	return $data;
}

function signPaymentInitData( $data, $privateKey, $privateKeyPassword) {
	
	$cart2Sign = $data["cart"][0]["name"] . "|" . $data["cart"][0]["quantity"] . "|" . $data["cart"][0]["amount"] . "|" . $data["cart"][0]["description"] . "|" 
		. $data["cart"][1]["name"] . "|" . $data["cart"][1]["quantity"] . "|" . $data["cart"][1]["amount"];
	
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
	
	return sign ( $data2Sign, $privateKey, $privateKeyPassword, "payment/init sign");
	
}


function createPaymentProcessUrl($merchantId, $payId, $dttm, $privateKey, $privateKeyPassword) {
	$text =  $merchantId . "|" . $payId . "|" . $dttm;
	$signature = sign($text, $privateKey, $privateKeyPassword, "payment/process sign");
	return $merchantId . "/" . $payId . "/" . $dttm . "/" . urlencode($signature); 
}


function verifyResponse($response, $key, $logMsg) {
	$text = $response -> payId . "|" . $response -> dttm  . "|" . $response -> resultCode . "|" . $response -> resultMessage;
	
	if(!is_null($response -> paymentStatus)) {
		$text = $text  . "|" . $response -> paymentStatus;
	}
	
	if(!is_null($response -> authCode)) {
		$text = $text  . "|" . $response -> authCode;
	}

	if(!is_null($response -> merchantData)) {
		$text = $text  . "|" . $response -> merchantData;
	}
	return verify($text, $response -> signature, $key, $logMsg);
}


function sign($text, $key, $passwd, $logMsg = null) {
	$logger = new Logger();
	$logger->write($logMsg . ": " . $text);
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
