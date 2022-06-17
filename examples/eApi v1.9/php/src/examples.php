<?php
namespace Monetplus;
use Monetplus\LoanInitRequest;

$loader = require dirname(__DIR__) . '/vendor/autoload.php';
use Monolog\Logger;
use Monolog\Handler\StreamHandler;
use JsonMapper\JsonMapperFactory;
use Commando\Command as Command;
use Monetplus\ApiService;

const MERCHANT_ID = 'M1MIPSXXXX';
const API_URL = 'https://iapi.iplatebnibrana.csob.cz/api/v1.9';
const PRIVATE_KEY_FILENAME = "./config/M1MIPSXXXX.key";
const MIPS_PUBLIC_KEY_FILENAME = "./config/mips_iplatebnibrana.csob.cz.pub";
const PRIVATE_KEY_PASSWORD = null;
const MIPS_PUBLIC_KEY_PASSWORD = null;

$examples_cmd = new Command();
$examples_cmd->useDefaultHelp(true);

$logger = new Logger('Examples');
$logger->pushHandler(new StreamHandler('php://stdout', Logger::INFO));

$examples_cmd->option('e') 
    ->aka('event');
$examples_cmd->option('c')
    ->aka('customerId');
$examples_cmd->option('r')
    ->aka('reason');
$examples_cmd->option('t')
    ->aka('payload');
$examples_cmd->option('x')
    ->aka('origPayId');
$examples_cmd->option('i')
    ->aka('initFile');    
$examples_cmd->option('p')
    ->aka('payId');
$examples_cmd->option('a')
    ->aka('amount');       

$examples_cmd->option('m')
    ->aka('mode')
    ->require()
    ->must(function ($mode) {
        $modes = array('PAYMENT_INIT',
            'PAYMENT_PROCESS',
            'PAYMENT_STATUS',
            'PAYMENT_CLOSE',
            'PAYMENT_REVERSE',
            'PAYMENT_REFUND',
            'ONECLICK_ECHO',
            'ONECLICK_INIT',
            'ONECLICK_PROCESS',
            'ECHO_GET',
            'ECHO_POST',
            'APPLEPAY_ECHO',
            'APPLEPAY_INIT',
            'APPLEPAY_PROCESS',
            'GOOGLEPAY_ECHO',
            'GOOGLEPAY_INIT',
            'GOOGLEPAY_PROCESS',
            'BUTTON_INIT',
            'MALLPAY_INIT',
            'MALLPAY_CANCEL',
            'MALLPAY_LOGISTICS',
            'MALLPAY_REFUND',
            'LOAN_INIT',
            'LOAN_CANCEL',
            'LOAN_LOGISTICS',
            'LOAN_REFUND',
            'ECHO_CUSTOMER'
        );
        return in_array($mode, $modes);
    });
$service = new ApiService(new MipsConnector(API_URL), 
new CryptoService(PRIVATE_KEY_FILENAME, PRIVATE_KEY_PASSWORD , MIPS_PUBLIC_KEY_FILENAME, MIPS_PUBLIC_KEY_PASSWORD));
$mapper = (new JsonMapperFactory())->bestFit();
if(!$service->handshake()) {
    $logger->error("unable to connect to mips server");
    return;
}

switch ($examples_cmd['mode']) {

    case "MALLPAY_INIT":
        $fileName = $examples_cmd["initFile"];
        if(strlen($fileName) == 0) {
            $logger->error("filename with json data not specified via -i option");
            break;
        }
        $content = file_get_contents($fileName);
        if(!$content) {
            $logger->error("can't read json file", ["fileName" => $fileName]);
            break;
        }
        $request = new MallpayInitRequest();
        $mapper->mapObject(json_decode($content), $request);
        $request->merchantId = MERCHANT_ID;
        
        $response = $service->mallpayInit($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;

    case "MALLPAY_LOGISTICS":
        $fileName = $examples_cmd["initFile"];
        if(strlen($fileName) == 0) {
            $logger->error("filename with json data not specified via -i option");
            break;
        }
        $content = file_get_contents($fileName);
        if(!$content) {
            $logger->error("can't read json file", ["fileName" => $fileName]);
            break;
        }
        $request = new MallpayLogisticsRequest();
        $mapper->mapObject(json_decode($content), $request);
        $request->merchantId = MERCHANT_ID;
        if(strlen($examples_cmd["payId"])> 0) {
            $request->payId = $examples_cmd["payId"];
        }
        if(!isset($request->payId) || strlen($request->payId)==0) {
            $logger->error("payId not specified (init file or -p option)");
            break;
        }
        $response = $service->mallpayLogistics($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }

        break;

    case "MALLPAY_CANCEL":
        $payId = $examples_cmd['payId'];
        if(strlen($payId) == 0) {
            $logger->error("payId not specified via -p option");
            break;
        }
        $reason = $examples_cmd['reason'];
        if(strlen($reason) == 0) {
            $logger->error("reason not specified via -r option, avaiable options are: aborted | other_payment | undeliverable | unavailable | abandoned | changed | unprocessed");
            break;
        }
        $request = new MallpayCancelRequest(MERCHANT_ID, $payId, $reason);
        $response = $service->mallpayCancel($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;

    case "MALLPAY_REFUND":
        $fileName = $examples_cmd["initFile"];
        if(strlen($fileName) == 0) {
            $logger->error("filename with json data not specified via -i option");
            break;
        }
        $content = file_get_contents($fileName);
        if(!$content) {
            $logger->error("can't read json file", ["fileName" => $fileName]);
            break;
        }
        $request = new MallpayRefundRequest();
        $mapper->mapObject(json_decode($content), $request);
        $request->merchantId = MERCHANT_ID;
        if(strlen($examples_cmd["payId"])> 0) {
            $request->payId = $examples_cmd["payId"];
        }
        if(!isset($request->payId) || strlen($request->payId)==0) {
            $logger->error("payId not specified (init file or -p option)");
            break;
        } 
        $response = $service->mallpayRefund($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }           
        break;

    case "LOAN_INIT":
        $fileName = $examples_cmd["initFile"];
        if(strlen($fileName) == 0) {
            $logger->error("filename with json data not specified via -i option");
            break;
        }
        $content = file_get_contents($fileName);
        if(!$content) {
            $logger->error("can't read json file", ["fileName" => $fileName]);
            break;
        }
        $request = new LoanInitRequest();
        $mapper->mapObject(json_decode($content), $request);
        if(strlen($examples_cmd['amount'])>0) {
            $request->totalAmount = $examples_cmd['amount'];
        }
        if(!isset($request->totalAmount) || $request->totalAmount<=0) {
            $logger->error("totalAmount not specified (init file or -a option)");
            break;
        }
        $request->merchantId = MERCHANT_ID;
        
        $response = $service->loanInit($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;

    case "LOAN_CANCEL":
        $payId = $examples_cmd['payId'];
        if(strlen($payId) == 0) {
            $logger->error("payId not specified via -p option");
            break;
        }
        $reason = $examples_cmd['reason'];
        if(strlen($reason) == 0) {
            $logger->error("reason not specified via -r option, avaiable options are: aborted | other_payment | undeliverable | unavailable | abandoned | changed | unprocessed");
            break;
        }
        $request = new LoanCancelRequest(MERCHANT_ID, $payId, $reason);
        $response = $service->loanCancel($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;
            
    case "LOAN_LOGISTICS":
        $payId = $examples_cmd['payId'];
        if(strlen($payId) == 0) {
            $logger->error("payId not specified via -p option");
            break;
        }
        $event = $examples_cmd['event'];
        if(strlen($event) == 0) {
            $logger->error("event not specified via -e option, avaiable options are delivered | picked_up | dispatched | ready_for_pickup");
            break;
        }        
        $request = new LoanLogisticsRequest(MERCHANT_ID, $payId, $event);
        $response = $service->loanLogistics($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
    
        break;

    case "ECHO_POST":
        $response = $service->echoPost(new EchoRequest(MERCHANT_ID));
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        } 
        break;

    case "ECHO_GET":
        $response = $service->echoGet(new EchoRequest(MERCHANT_ID));
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        } 
        break;           

    case "ECHO_CUSTOMER":
        $customerId = $examples_cmd['customerId'];
        if(strlen($customerId) == 0) {
            $logger->error("customerId not specified via -c option");
            break;
        }
        $response = $service->echoCustomer(new EchoCustomerRequest(MERCHANT_ID, $customerId));
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        } 
        break;

    case "LOAN_REFUND":
        $fileName = $examples_cmd["initFile"];
        if(strlen($fileName) == 0) {
            $logger->error("filename with json data not specified via -i option");
            break;
        }
        $content = file_get_contents($fileName);
        if(!$content) {
            $logger->error("can't read json file", ["fileName" => $fileName]);
            break;
        }
        $request = new LoanRefundRequest();
        $mapper->mapObject(json_decode($content), $request);
        $request->merchantId = MERCHANT_ID;
        if(strlen($examples_cmd["payId"])> 0) {
            $request->payId = $examples_cmd["payId"];
        }
        if(!isset($request->payId) || strlen($request->payId)==0) {
            $logger->error("payId not specified (init file or -p option)");
            break;
        } 
        $response = $service->loanRefund($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }           
        break;  

    case "BUTTON_INIT":
        $fileName = $examples_cmd["initFile"];
        if(strlen($fileName) == 0) {
            $logger->error("filename with json data not specified via -i option");
            break;
        }
        $content = file_get_contents($fileName);
        if(!$content) {
            $logger->error("can't read json file", ["fileName" => $fileName]);
            break;
        }
        $request = new ButtonInitRequest();
        $mapper->mapObject(json_decode($content), $request);
        $request->merchantId = MERCHANT_ID;
        $response = $service->buttonInit($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus, "redirect" => $response->redirect]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;

    case "GOOGLEPAY_ECHO":
        $request = new EchoRequest(MERCHANT_ID);
        $response = $service->googlepayEcho($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["initParams" => $response->initParams]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;
    case "GOOGLEPAY_INIT":
        $fileName = $examples_cmd["initFile"];
        if(strlen($fileName) == 0) {
            $logger->error("filename with json data not specified via -i option");
            break;
        }
        $content = file_get_contents($fileName);
        if(!$content) {
            $logger->error("can't read json file", ["fileName" => $fileName]);
            break;
        }
        $request = new GooglepayInitRequest();
        $mapper->mapObject(json_decode($content), $request);
        $request->merchantId = MERCHANT_ID;
        if(strlen($examples_cmd['payload'])>0) {
            $request->payload = $examples_cmd['payload'];
        }
        if(!isset($request->payload) || strlen($request->payload)==0) {
            $logger->error("payload not specified (init file or -t option)");
            break;
        }
        $response = $service->googlepayInit($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;
    case "GOOGLEPAY_PROCESS":
        $payId = $examples_cmd['payId'];
        if(strlen($payId) == 0) {
            $logger->error("payId not specified via -p option");
            break;
        }
        $request = new GooglepayProcessRequest(MERCHANT_ID, $payId, createTestFingerprint());
        $response = $service->googlepayProcess($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;   

    case "APPLEPAY_ECHO":
        $request = new EchoRequest(MERCHANT_ID);
        $response = $service->applepayEcho($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["initParams" => $response->initParams]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;
    case "APPLEPAY_INIT":
        $fileName = $examples_cmd["initFile"];
        if(strlen($fileName) == 0) {
            $logger->error("filename with json data not specified via -i option");
            break;
        }
        $content = file_get_contents($fileName);
        if(!$content) {
            $logger->error("can't read json file", ["fileName" => $fileName]);
            break;
        }
        $request = new ApplepayInitRequest();
        $mapper->mapObject(json_decode($content), $request);
        $request->merchantId = MERCHANT_ID;
        if(strlen($examples_cmd['payload'])>0) {
            $request->payload = $examples_cmd['payload'];
        }
        if(!isset($request->payload) || strlen($request->payload)==0) {
            $logger->error("payload not specified (init file or -t option)");
            break;
        }

        $response = $service->applepayInit($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;

    case "APPLEPAY_PROCESS":
        $payId = $examples_cmd['payId'];
        if(strlen($payId) == 0) {
            $logger->error("payId not specified via -p option");
            break;
        }
        $request = new ApplepayProcessRequest(MERCHANT_ID, $payId, createTestFingerprint());
        $response = $service->applepayProcess($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;

    case "ONECLICK_ECHO":
        $origPayId = $examples_cmd['origPayId'];
        if(strlen($origPayId) == 0) {
            $logger->error("original pay id not specified via -x option");
            break;
        }
        $request = new OneclickEchoRequest();
        $request->merchantId = MERCHANT_ID;
        $request->origPayId = $origPayId;
        $response = $service->oneclickEcho($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded");
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;
    case "ONECLICK_INIT":
        $origPayId = $examples_cmd['origPayId'];
        $fileName = $examples_cmd["initFile"];
        if(strlen($fileName) == 0) {
            $logger->error("filename with json data not specified via -i option");
            break;
        }
        $content = file_get_contents($fileName);
        if(!$content) {
            $logger->error("can't read json file", ["fileName" => $fileName]);
            break;
        }
        $request= new OneclickInitRequest();
        $mapper->mapObject(json_decode($content), $request);
        if(strlen($origPayId) > 0) {
            $request->origPayId = $origPayId;
        }
        $request->merchantId = MERCHANT_ID;
        $response = $service->oneclickInit($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;
    case "ONECLICK_PROCESS":
        $payId = $examples_cmd['payId'];
        if(strlen($payId) == 0) {
            $logger->error("payId not specified via -p option");
            break;
        }
        $request = new OneclickProcessRequest();
        $request->merchantId = MERCHANT_ID;
        $request->payId = $payId;
        $request->fingerprint = createTestFingerprint();
        $response = $service->oneclickProcess($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;
    case "PAYMENT_STATUS":
        $payId = $examples_cmd['payId'];
        if(strlen($payId) == 0) {
            $logger->error("payId not specified via -p option");
            break;
        }
        $request = new PaymentStatusRequest();
        $request->merchantId = MERCHANT_ID;
        $request->payId = $payId;
        $response = $service->paymentStatus($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId, "paymentStatus" => $response->paymentStatus]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;
    case "PAYMENT_INIT":
        $fileName = $examples_cmd["initFile"];
        if(strlen($fileName) == 0) {
            $logger->error("filename with json data not specified via -i option");
            break;
        }
        $content = file_get_contents($fileName);
        if(!$content) {
            $logger->error("can't read json file", ["fileName" => $fileName]);
            break;
        }
        $request= new PaymentInitRequest();
        $mapper->mapObject(json_decode($content), $request);
        $request->merchantId = MERCHANT_ID;
        $response = $service->paymentInit($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["payId" => $response->payId]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;
    case "PAYMENT_CLOSE":
        $payId = $examples_cmd['payId'];
        if(strlen($payId) == 0) {
            $logger->error("payId not specified via -p option");
            break;
        }
        $request = new PaymentCloseRequest();
        $request->merchantId = MERCHANT_ID;
        $request->payId = $payId;
        if(strlen($examples_cmd['amount'])>0) {
            $request->totalAmount = $examples_cmd['amount'];
        }
        $response = $service->paymentClose($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded");
        } 
        break;
    case "PAYMENT_REVERSE":
        $payId = $examples_cmd['payId'];
        if(strlen($payId) == 0) {
            $logger->error("payId not specified via -p option");
            break;
        }
        $request = new PaymentReverseRequest();
        $request->merchantId = MERCHANT_ID;
        $request->payId = $payId;
        $response = $service->paymentReverse($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded");
        } 
        break;
    case "PAYMENT_REFUND":
        $payId = $examples_cmd['payId'];
        if(strlen($payId) == 0) {
            $logger->error("payId not specified via -p option");
            break;
        }
        $request = new PaymentRefundRequest();
        $request->merchantId = MERCHANT_ID;
        $request->payId = $payId;
        if(strlen($examples_cmd['amount'])>0) {
            $request->totalAmount = $examples_cmd['amount'];
        }
        $response = $service->paymentRefund($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded");
        } 
        break;
    case "PAYMENT_PROCESS":
        $payId = $examples_cmd['payId'];
        if(strlen($payId) == 0) {
            $logger->error("payId not specified via -p option");
            break;
        }
        $request = new PaymentProcessRequest();
        $request->merchantId = MERCHANT_ID;
        $request->payId = $payId;
        $url = $service->paymentProcess($request);
        if($response->resultCode == 0) {
            $logger->info("Operation succeeded", ["url" => $url]);
        } else {
            $logger->error("Operation fail", ["resultCode" => $response->resultCode, "resultMessage" => $response->resultMessage]);
        }
        break;
}

function createTestFingerprint() : AuthData {
    $fingerprint = new AuthData();
    $fingerprint->browser = new Auth3dsBrowser();
    $fingerprint->browser->javaEnabled  = false;
    $fingerprint->browser->language = "cs-CZ";
    $fingerprint->browser->colorDepth = 24;
    $fingerprint->browser->screenHeight = 720;
    $fingerprint->browser->screenWidth =1280;
    $fingerprint->browser->timezone = -60;
    $fingerprint->browser->javascriptEnabled = true;
    $fingerprint->browser->challengeWindowSize = "01";
    $fingerprint->browser->acceptHeader = "application/json, text/plain, */*";
    $fingerprint->browser->userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36";
    return $fingerprint;   
}

?>