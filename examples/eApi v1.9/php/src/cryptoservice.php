<?php
namespace Monetplus;
use Monolog\Logger;
use Monolog\Handler\StreamHandler;
require dirname(__DIR__) . '/vendor/autoload.php';


class CryptoService
{
    private Crypto $crypto;
    private Logger $logger;

    private $privateKeyId;
    private $publicKeyId;

    private $SKIP_VERIFICATION = false;
    function __construct(string $privateKeyFile, $privateKeyPassword, string $mipsPublicKeyFile, $mipsKeypasswor)
    {
        $fp = fopen($privateKeyFile, "r");
        if (!$fp) {
            throw new \Exception("Key not found");
        }
        $private = fread($fp, filesize($privateKeyFile));
        fclose($fp);
        $this->privateKeyId = openssl_get_privatekey($private, $privateKeyPassword);
        $this->logger = new Logger('CryptoService');
        $this->logger->pushHandler(new StreamHandler('php://stdout', Logger::INFO));
        $this->logger->pushHandler(new StreamHandler('php://stderr', Logger::ERROR));

        $fp = fopen($mipsPublicKeyFile, "r");
        if (!$fp) {
            throw new \Exception("Key not found");
        }
        $public = fread($fp, filesize($mipsPublicKeyFile));
        fclose($fp);
        $this->publicKeyId = openssl_get_publickey($public);
    }

    function createSignature($signBase): SignBase
    {
	date_default_timezone_set('Europe/Prague');
        $date = date("YmdHis");
        $signBase->dttm = $date; //"20000101010101";
        $this->logger->info("cryptoService", ["toSign" => $signBase->toSign()]);
        openssl_sign($signBase->toSign(), $signature, $this->privateKeyId, OPENSSL_ALGO_SHA256);
        $signature = base64_encode($signature);
        // $toSign->signature = "YmQyODk2NjY5MDIzOThlNDQzYzUzMzY5NjZjNDhkOGM4ZjRiOGNjZGJlMmFkMzhiYjZmODg0NTE0NzdkNDQwZA==";
        $signBase->signature = $signature;
        return $signBase;
    }

    function verifySignature($signBase, $signature)
    {
        if (!$this->SKIP_VERIFICATION) {
            $signature = base64_decode($signature);
            $res = openssl_verify($signBase->toSign(), $signature, $this->publicKeyId, OPENSSL_ALGO_SHA256);
            $this->logger->info("verify signature", ["res" => $res, "toSign" => $signBase->toSign(), "object" => $signBase]);
            if ($res != '1') {
                throw new \Exception("Invalid signature");
            }
        }
    }
}
?>