<?php

require_once ('logger.php');
require_once ('struct.php');

/**
 * Creates cart with exact two items (goods, postage)
 * @param string goodsDesc description of goods
 * @param double totalAmount total order amount in CZK
 * @param double shippingTotal shipping amount in CZK
 * @return array cartData
 */
function createCartData($goodsDesc, $totalAmount, $shippingTotal) {
    $goodsDesc = mb_substr(trim($goodsDesc), 0, 37, 'utf-8') . "...";
    $totalAmount = (int) ($totalAmount * 100);
    $shippingTotal = (int) ($shippingTotal * 100);

    $cartData = array(
        0 => array(
            "name" => Constants::$SHOP_CART_NAME,
            "quantity" => Constants::$SHOP_CART_QUANTITY,
            "amount" => $totalAmount - $shippingTotal,
            "description" => $goodsDesc
        ),
        1 => array(
            "name" => Constants::$SHOP_SHIPPING,
            "quantity" => Constants::$SHOP_CART_QUANTITY,
            "amount" => $shippingTotal,
            "description" => Constants::$SHOP_SHIPPING_DESC
        )
    );
    return $cartData;
}

/**
 * Creates JSON data for payment/init including signature, more on
 * https://github.com/csob/paymentgateway/wiki/Z%C3%A1kladn%C3%AD-metody#metoda-paymentinit-
 *
 * @param string merchantId ID of merchant assigned by bank
 * @param string orderNo reference order number
 * @param string dttm date and time ot request sending, format YYYYMMDDHHMMSS
 * @param double totalAmount total order amount
 * @param string returnUrl return URL address for back redirecting from payment gateway to e-shop
 * @param object|array cart cart structured data
 * @param string customerId customer ID
 * @param string privateKey merchant private key for data signing
 * @param string privateKeyPassword merchant private key password
 * @param bool|int closePayment close payment flag (0/1)
 * @param string merchantData merchant base64 encoded data
 * @param string returnMethodPOST indicator if return method POST should be used (yes/no)
 * @param string currency Currency of transaction
 * @param string language Language of payment gateway
 * @return array paymentInitData
 */
function createPaymentInitData($merchantId, $orderNo, $dttm, $totalAmount, $returnUrl, $cart, $customerId, $privateKey, $privateKeyPassword, $closePayment, $merchantData, $returnMethodPOST, $currency, $language) {

    $payOperation = Constants::$PAYOPERATION;
    $payMethod = Constants::$PAYMETHOD;
    $returnMethod = ($returnMethodPOST == 'yes') ? Constants::$POST_RETURNMETHOD : Constants::$GET_RETURNMETHOD;
    $totalAmount = (int) ($totalAmount * 100);

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
        "merchantData" => $merchantData,
        "language" => $language
    );
    if (!is_null($customerId) && $customerId != '0') {
        $data["customerId"] = $customerId;
    }

    $data["signature"] = signPaymentInitData($data, $privateKey, $privateKeyPassword);

    return $data;
}

function signPaymentInitData($data, $privateKey, $privateKeyPassword) {

    $cart2Sign = $data["cart"][0]["name"] . "|" . $data["cart"][0]["quantity"] . "|" . $data["cart"][0]["amount"] . "|" . $data["cart"][0]["description"] . "|"
            . $data["cart"][1]["name"] . "|" . $data["cart"][1]["quantity"] . "|" . $data["cart"][1]["amount"] . "|" . $data["cart"][1]["description"];

    $data2Sign = $data["merchantId"] . "|" . $data["orderNo"] . "|" . $data["dttm"] . "|" . $data["payOperation"] . "|" . $data["payMethod"] . "|" . $data["totalAmount"]
            . "|" . $data["currency"] . "|" . ($data["closePayment"] ? 'true' : 'false') . "|" . $data["returnUrl"] . "|" . $data["returnMethod"] . "|" . $cart2Sign;

    $merchantData = $data["merchantData"];
    if (!is_null($merchantData)) {
        $data2Sign = $data2Sign . "|" . $merchantData;
    }

    if (isset($data["customerId"]) && $data["customerId"] != '0') {
        $data2Sign = $data2Sign . "|" . $data["customerId"];
    }

    $data2Sign = $data2Sign . "|" . $data["language"];

    if ($data2Sign [strlen($data2Sign) - 1] == '|') {
        $data2Sign = substr($data2Sign, 0, strlen($data2Sign) - 1);
    }

    echo "data to sign:\n\"" . htmlspecialchars($data2Sign, ENT_QUOTES) . "\"\n\n";

    return sign($data2Sign, $privateKey, $privateKeyPassword, "payment/init data to sign:");
}

function createGetParams($merchantId, $payId, $dttm, $privateKey, $privateKeyPassword) {
    $text = $merchantId . "|" . $payId . "|" . $dttm;
    $signature = sign($text, $privateKey, $privateKeyPassword, "data to sign:");
    return $merchantId . "/" . $payId . "/" . $dttm . "/" . urlencode($signature);
}

function preparePutRequest($merchantId, $payId, $dttm, $privateKey, $privateKeyPassword) {
    $data = array(
        "merchantId" => $merchantId,
        "payId" => $payId,
        "dttm" => $dttm
    );
    $text = $merchantId . "|" . $payId . "|" . $dttm;
    $data['signature'] = sign($text, $privateKey, $privateKeyPassword, "data to sign:");
    return $data;
}

function verifyResponse($response, $key, $logMsg) {
    $text = $response ['payId'] . "|" . $response ['dttm'] . "|" . $response ['resultCode'] . "|" . $response ['resultMessage'];

    if (!is_null($response ['paymentStatus'])) {
        $text = $text . "|" . $response ['paymentStatus'];
    }

    if (isset($response ['authCode']) && !is_null($response ['authCode'])) {
        $text = $text . "|" . $response ['authCode'];
    }

    if (isset($response ['merchantData']) && !is_null($response ['merchantData'])) {
        $text = $text . "|" . $response ['merchantData'];
    }

    return verify($text, $response ['signature'], $key, $logMsg);
}

function sign($text, $key, $passwd, $logMsg = null) {
    $logger = new Logger();
    $logger->write($logMsg . ": '" . $text . "'");
    $path = dirname(__FILE__) . "/keys/";
    $key = $path . $key;
    $fp = fopen($key, "r");
    if (!$fp) {
        throw new Exception("Key not found");
    }
    $private = fread($fp, filesize($key));
    fclose($fp);
    $privateKeyId = openssl_get_privatekey($private, $passwd);
    openssl_sign($text, $signature, $privateKeyId,  OPENSSL_ALGO_SHA256);
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
    $res = openssl_verify($text, $signature, $publicKeyId, OPENSSL_ALGO_SHA256);
    openssl_free_key($publicKeyId);
    return (($res != '1') ? false : true);
}
