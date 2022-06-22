<?php

class Constants {

    static $SHOP_CART_NAME = 'Wireless headphones';
    static $SHOP_CART_QUANTITY = 1;
    static $SHOP_SHIPPING = 'Shipping';
    static $SHOP_SHIPPING_DESC = 'DPL';
    static $LANGUAGE = [
        'cs' => 'česky',
        'sk' => 'slovensky',
        'en' => 'anglicky',
        'de' => 'německy',
        'ru' => 'rusky',
        'es' => 'španělsky',
        'pt' => 'portugalsky',
        'it' => 'italsky',
        'fr' => 'francouzsky',
        'hu' => 'maďarsky',
        'ro' => 'rumunsky',
        'tr' => 'turecky',
        'pl' => 'polsky',
        'ja' => 'japonsky',
        'vi' => 'vietnamsky',
        'hr' => 'chorvatsky',
        'sl' => 'slovinsky',
        'sv' => 'švédsky'
    ];
    static $CURRENCY = [
        'CZK' => 'Česká koruna',
        'HRK' => 'Chorvatská kuna',
        'HUF' => 'Maďarský forint',
        'GBP' => 'Britská libra',
        'USD' => 'Americký dolar',
        'EUR' => 'Euro',
        'PLN' => 'Polský złoty',
        'RON' => 'Rumunské leu',
        'SEK' => 'Švédská koruna',
        'NOK' => 'Norská koruna'
    ];
    static $PAYMETHOD = 'card';
    static $PAYOPERATION = 'payment';
    static $GET_RETURNMETHOD = 'GET';
    static $POST_RETURNMETHOD = 'POST';

}

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
    static $echoCustomer = "/echo/customer/";

}
