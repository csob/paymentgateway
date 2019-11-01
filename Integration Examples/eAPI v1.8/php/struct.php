<?php

class Constants {

    static $SHOP_CART_NAME = 'Shopping at ...';
    static $SHOP_CART_QUANTITY = 1;
    static $SHOP_SHIPPING = 'Shipping';
    static $SHOP_SHIPPING_DESC = 'PPL';
    static $LANGUAGE = [
        'CZ' => 'Čestina',
        'SK' => 'Slovenština',
        'EN' => 'Angličtina',
        'DE' => 'Němčina',
        'RU' => 'Ruština',
        'ES' => 'Španělština',
        'PT' => 'Portugalsko',
        'IT' => 'Italština',
        'FR' => 'Francouzština',
        'HU' => 'Maďarština',
        'RO' => 'Rumunsko',
        'TR' => 'Vietnam',
        'PL' => 'Polsko',
        'JP' => 'Japonsko',
        'VN' => 'Vietnam',
        'HR' => 'Chorvatsko',
        'SL' => 'Slovinsko'
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
        'RUB' => 'Ruský rubl',
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
    static $customerInfo = "/customer/info/";

}
