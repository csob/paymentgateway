<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <link rel="stylesheet" href="bootstrap.min.css">
</head>

<body>
<pre>
<?php
require_once ('logger.php');
require_once ('crypto.php');
require_once ('setup.php');

$action = $_GET ['action'];

echo 'processing payment/' . $action . "\n\n";

$gateway_url = null;

$merchantId = $_GET ['merchant_id'];
$payId = $_GET ['pay_id'];

$dttm = (new DateTime ())->format ( "YmdHis" );

$params = createGetParams($merchantId, $payId, $dttm, $privateKey, $privateKeyPassword);

$data = null;
$custom_req = null;
switch ($action) {
    case 'status':
    	$gateway_url = $url .  NativeApiMethod::$status . $params;   
    	$custom_req = "GET";
    	break;
    case 'close':
    	$gateway_url = $url .  NativeApiMethod::$close;
    	$custom_req = "PUT";
		$data = preparePutRequest($merchantId, $payId, $dttm, $privateKey, $privateKeyPassword);
    	break;
    case 'reverse':
    	$gateway_url = $url .  NativeApiMethod::$reverse;   
    	$custom_req = "PUT";
		$data = preparePutRequest($merchantId, $payId, $dttm, $privateKey, $privateKeyPassword);
    	break;
    case 'refund':
    	$gateway_url = $url .  NativeApiMethod::$refund;   
    	$custom_req = "PUT";
		$data = preparePutRequest($merchantId, $payId, $dttm, $privateKey, $privateKeyPassword);
    	break;
}

echo "http method: " . $custom_req . "\n";
echo "gateway url: " . $gateway_url . "\n";
if (!is_null($data)) {
	echo "req: " . json_encode ( $data, JSON_UNESCAPED_SLASHES ) . "\n";
}

if (!is_null($action))
{
	$ch = curl_init ( $gateway_url );
	curl_setopt ( $ch, CURLOPT_RETURNTRANSFER, true );
	curl_setopt ( $ch, CURLOPT_SSL_VERIFYPEER, false);
	if ($custom_req == 'PUT') {
		$putData = json_encode ( $data, JSON_UNESCAPED_SLASHES );
		curl_setopt($ch, CURLOPT_CUSTOMREQUEST, $custom_req);
		curl_setopt($ch, CURLOPT_POSTFIELDS, $putData);
		curl_setopt ( $ch, CURLOPT_HTTPHEADER, array (
			'Content-Type: application/json',
			'Accept: application/json;charset=UTF-8'
		) );
	}
	else {
		curl_setopt ( $ch, CURLOPT_HTTPHEADER, array (
				'Accept: application/json;charset=UTF-8'
		) );
	}
	
	
	$result = curl_exec ( $ch );
	echo "\n";
	
	if(curl_errno($ch)) {
		echo 'payment/' . $action . ' failed, reason: ' . curl_error($ch);
		return;
	}
	
	$httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
	echo "http status: " . $httpCode . "\n\n";
	
	if($httpCode != 200) {
		echo 'payment/' . $action . ' failed, http response: ' . $httpCode;
		return;
	}
	curl_close($ch);
	
	echo 'response: ' . $result . "\n\n";
	
	$result_array = json_decode ( $result, true );
	if(is_null($result_array ['resultCode'])) {
		echo 'payment/' . $action . ' failed, missing resultCode';
		return;
	}

	if (verifyResponse($result_array, $publicKey, "payment/" . $action . " verify") == false) {
		echo 'payment/' . $action . ' failed, unable to verify signature';
		return;
	}
	
	if ($result_array ['resultCode'] != '0') {
		echo 'payment/' . $action . ' failed, reason: ' . $result_array ['resultMessage'];
	}
	
}


?>
</pre>
<a href="<?= $url . NativeApiMethod::$process . $params ?>">payment/process</a><br/>
<a href="payment.php?action=status&merchant_id=<?= $merchantId ?>&pay_id=<?= $payId ?>">payment/status</a><br/>
<a href="payment.php?action=close&merchant_id=<?= $merchantId ?>&pay_id=<?= $payId ?>">payment/close</a><br/>
<a href="payment.php?action=reverse&merchant_id=<?= $merchantId ?>&pay_id=<?= $payId ?>">payment/reverse</a><br/>
<a href="payment.php?action=refund&merchant_id=<?= $merchantId ?>&pay_id=<?= $payId ?>">payment/refund</a><br/>
<br/>
<a href="index.php">new payment/init</a><br/>
</body>
</html>