<?php

require_once ('logger.php');

class Constants {

    static $SHOP_CART_QUANTITY = 1;
    static $LANGUAGE = 'CZ';
    static $SUPPORTED_CURRENCY = ['CZK', 'HUF', 'GBP', 'USD', 'EUR', 'PLN'];
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
    $titles = '';
    // extract the titles of products for description
    foreach ($cart->get_cart() as $item => $values) {
        $product = $values['data'];
        $titles = ' ' . $product->get_title();
    }
    $titles = mb_substr(trim($titles), 0, 37, 'utf-8') . "...";
    $totalAmount = $totalAmount * 100;

    $shippingTotal = $cart->get_shipping_total();
    $shippingTotal = (int) $shippingTotal * 100;
    $cartData = array(
        0 => array(
            "name" => mb_substr(trim($firstCartItemDesc), 0, 20, 'utf-8'),
            "quantity" => Constants::$SHOP_CART_QUANTITY,
            "amount" => $totalAmount - $shippingTotal,
            "description" => $titles
        ),
        1 => array(
            "name" => mb_substr(trim($secondCartItemDesc), 0, 20, 'utf-8'),
            "quantity" => Constants::$SHOP_CART_QUANTITY,
            "amount" => $shippingTotal
        )
    );
    return $cartData;
}

/**
 * Creates JSON data for payment/init including signature
 * @param string merchantId ID of merchant assigned by bank
 * @param string orderNo reference order number
 * @param string dttm date and time ot request sending, format YYYYMMDDHHMMSS
 * @param double totalAmount total order amount
 * @param string returnUrl return URL address for back redirecting from payment gateway to e-shop
 * @param object|array cart cart structured data
 * @param string description brief payment description
 * @param string customerId customer ID
 * @param string privateKey merchant private key for data signing
 * @param string privateKeyPassword merchant private key password
 * @param bool|int closePayment close payment flag (0/1)
 * @param string merchantData merchant base64 encoded data
 * @param string returnMethodPOST indicator if return method POST should be used
 * @param array paymentInitData
 */
function createPaymentInitData($merchantId, $orderNo, $dttm, $totalAmount, $returnUrl, $cart, $description, $customerId, $privateKey, $privateKeyPassword, $closePayment, $merchantData, $returnMethodPOST, $ttlSec, $logoVersion, $colorSchemeVersion) {

    $payOperation = Constants::$PAYOPERATION;
    $payMethod = Constants::$PAYMETHOD;
    $currency = get_woocommerce_currency();

    $returnMethod = ($returnMethodPOST == 'yes') ? Constants::$POST_RETURNMETHOD : Constants::$GET_RETURNMETHOD;
    $closePayment = ($closePayment == '1') ? "true" : "false";
    $totalAmount = (int) $totalAmount * 100;
    $titles = $cart[0]['description'];
    $shippingTotal = $cart[1]['amount'];

    $data = array(
        "merchantId" => $merchantId,
        "orderNo" => $orderNo,
        "dttm" => $dttm,
        "payOperation" => $payOperation,
        "payMethod" => $payMethod,
        "totalAmount" => $totalAmount,
        "currency" => $currency,
        "closePayment" => $closePayment,
        "returnUrl" => $returnUrl,
        "returnMethod" => $returnMethod,
        "cart" => $cart,
        "description" => $description,
        "merchantData" => $merchantData
    );
    if (!is_null($customerId) && $customerId != '0') {
        $data["customerId"] = $customerId;
    }
    $data["language"] = Constants::$LANGUAGE;
    if (!is_null($ttlSec) && $ttlSec != '') {
        $data["ttlSec"] = (int) $ttlSec;
    }
    if (!is_null($logoVersion) && $logoVersion != '') {
        $data["logoVersion"] = (int) $logoVersion;
    }
    if (!is_null($colorSchemeVersion) && $colorSchemeVersion != '') {
        $data["colorSchemeVersion"] = (int) $colorSchemeVersion;
    }

    $data["signature"] = signPaymentInitData($data, $privateKey, $privateKeyPassword);

    return $data;
}

function signPaymentInitData($data, $privateKey, $privateKeyPassword) {

    $cart2Sign = $data["cart"][0]["name"] . "|" . $data["cart"][0]["quantity"] . "|" . $data["cart"][0]["amount"] . "|" . $data["cart"][0]["description"] . "|"
            . $data["cart"][1]["name"] . "|" . $data["cart"][1]["quantity"] . "|" . $data["cart"][1]["amount"];

    $data2Sign = $data["merchantId"] . "|" . $data["orderNo"] . "|" . $data["dttm"] . "|" . $data["payOperation"] . "|" . $data["payMethod"] . "|" . $data["totalAmount"]
            . "|" . $data["currency"] . "|" . $data["closePayment"] . "|" . $data["returnUrl"] . "|" . $data["returnMethod"] . "|" . $cart2Sign . "|" . $data["description"];

    $merchantData = $data["merchantData"];
    if (!is_null($merchantData)) {
        $data2Sign = $data2Sign . "|" . $merchantData;
    }

    $customerId = $data["customerId"];
    if (!is_null($customerId) && $customerId != '0') {
        $data2Sign = $data2Sign . "|" . $customerId;
    }

    $data2Sign = $data2Sign . "|" . $data["language"];

    $ttlSec = $data["ttlSec"];
    if (!is_null($ttlSec) && $ttlSec != '0') {
        $data2Sign = $data2Sign . "|" . $ttlSec;
    }

    $logoVersion = $data["logoVersion"];
    if (!is_null($logoVersion)) {
        $data2Sign = $data2Sign . "|" . $logoVersion;
    }

    $colorSchemeVersion = $data["colorSchemeVersion"];
    if (!is_null($colorSchemeVersion)) {
        $data2Sign = $data2Sign . "|" . $colorSchemeVersion;
    }
    if ($data2Sign [strlen($data2Sign) - 1] == '|') {
        $data2Sign = substr($data2Sign, 0, strlen($data2Sign) - 1);
    }

    return sign($data2Sign, $privateKey, $privateKeyPassword, "payment/init sign");
}

function createPaymentProcessUrl($merchantId, $payId, $dttm, $privateKey, $privateKeyPassword) {
    $text = $merchantId . "|" . $payId . "|" . $dttm;
    $signature = sign($text, $privateKey, $privateKeyPassword, "payment/process sign");
    return $merchantId . "/" . $payId . "/" . $dttm . "/" . urlencode($signature);
}

function verifyResponse($response, $key, $logMsg) {
    $text = $response->payId . "|" . $response->dttm . "|" . $response->resultCode . "|" . $response->resultMessage;

    if (!is_null($response->paymentStatus)) {
        $text = $text . "|" . $response->paymentStatus;
    }

    if (!is_null($response->authCode)) {
        $text = $text . "|" . $response->authCode;
    }

    if (!is_null($response->merchantData)) {
        $text = $text . "|" . $response->merchantData;
    }
    return verify($text, $response->signature, $key, $logMsg);
}

function sign($text, $key, $passwd, $logMsg = null) {
    $logger = new Logger();
    $logger->write($logMsg . ": " . $text);
    $path = dirname(__FILE__) . "/keys/";
    $key = $path . $key;
    $fp = fopen($key, "r");
    if (!$fp) {
        throw new Exception("Key not found");
    }
    $private = fread($fp, filesize($key));
    fclose($fp);
    $privateKeyId = openssl_get_privatekey($private, $passwd);
    openssl_sign($text, $signature, $privateKeyId);
    $signature = base64_encode($signature);
    openssl_free_key($privateKeyId);
    return $signature;
}

function verify($text, $signatureBase64, $key, $logMsg = null) {
    $logger = new Logger();
    $logger->write($logMsg . ": " . $text);
    $path = dirname(__FILE__) . "/keys/";
    $key = $path . $key;
    $fp = fopen($key, "r");
    if (!$fp) {
        throw new Exception("Key not found");
    }
    $public = fread($fp, filesize($key));
    fclose($fp);
    $publicKeyId = openssl_get_publickey($public);
    $signature = base64_decode($signatureBase64);
    $res = openssl_verify($text, $signature, $publicKeyId);
    openssl_free_key($publicKeyId);
    return (($res != '1') ? false : true);
}
