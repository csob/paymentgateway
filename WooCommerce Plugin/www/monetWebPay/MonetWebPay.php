<?php

require_once(dirname(__FILE__) . "/crypto.php");
require_once(dirname(__FILE__) . "/logger.php");
require_once(dirname(__FILE__) . "/struct.php");

class MonetWebPay {

    public $log = null;
    private $mysqlConfig = null;
    private $dbConnect = null;

    public function __construct($databaseConfig) {
        //global $mysql;
        $this->log = new Logger();
        $this->mysqlConfig = array(
            'host' => $databaseConfig->host,
            'name' => $databaseConfig->name,
            'user' => $databaseConfig->user,
            'password' => $databaseConfig->password
        );
        $this->dbConnect = $this->sqlconnect($this->mysqlConfig);
    }

    public function verifyPaymentInitSignature($response, $publicKey, $logMsg) {
        return verifyResponse($response, $publicKey, $logMsg);
    }

    public function verifyPaymentResponseSignature($response, $publicKey, $logMsg) {
        return verifyResponse($response, $publicKey, $logMsg);
    }

    public function insertTransaction($response, $orderNumber, $cart) {
        $sql = "INSERT INTO monetTransaction(orderNumber, payId, paymentStatus, created, updated, paymentResponse, authCode, cart) VALUES ("
                . $this->toSql($orderNumber) . ", "
                . $this->toSql($response->payId) . ", "
                . $this->toSql($response->paymentStatus) . ", "
                . "NOW()" . ", "
                . $this->toSql(null) . ", "
                . $this->toSql($response->dttm) . ", "
                . $this->toSql(null) . ", "
                . $this->toSql(json_encode($cart)) . ")";
        $this->log->write($sql);
        $this->sqlExecute($sql);
    }

    public function updateTransaction($orderNumber, $response, $cart) {
        $sql = "update monetTransaction set cart = " . $this->toSql(json_encode($cart)) . ", authCode = " . $this->toSql($response->authCode) . ", paymentStatus=" . $this->toSql($response->paymentStatus) . ", updated=" . "NOW()" . ", paymentResponse=" . $this->toSql($response->dttm) . " where orderNumber = " . $this->toSql($orderNumber);
        $this->log->write($sql);
        $this->sqlExecute($sql);
    }

    public function updateTransactionStatus($orderNumber, $response) {
        $sql = "UPDATE monetTransaction SET authCode = " . $this->toSql($response->authCode) . ", paymentStatus=" . $this->toSql($response->paymentStatus) . ", updated=" . "NOW()" . ", paymentResponse=" . $this->toSql($response->dttm) . " WHERE orderNumber = " . $this->toSql($orderNumber);
        $this->log->write($sql);
        $this->sqlExecute($sql);
    }

    public function selectTransaction($orderNumber) {
        $sql = "SELECT * FROM monetTransaction WHERE orderNumber=" . $orderNumber;
        $result = $this->sqlExecute($sql);
        $row = mysqli_fetch_assoc($result);
        $this->log->write($sql);
        return $row;
    }

    function clearTransaction($orderNumber, $response) {
        $sql = "UPDATE monetTransaction SET paymentStatus = " . $this->toSql($response->paymentStatus) . ", authCode = " . $this->toSql(null) . ", updated=NOW(), payId=" . $this->toSql(null) . " WHERE orderNumber = " . $this->toSql($orderNumber);
        $this->log->write($sql);
        $this->sqlExecute($sql);
    }

    private function sqlconnect($mysql) {

        $dbconn = mysqli_connect($mysql['host'], $mysql['user'], $mysql['password']);
        mysqli_select_db($dbconn, $mysql['name']) or die(mysqli_error($this->dbConnect));
        mysqli_query($dbconn, "SET NAMES 'UTF-8'");
        return $dbconn;
    }

    private function sqlexecute($sql) {
        $res = mysqli_query($this->dbConnect, $sql) or trigger_error(mysqli_error($this->dbConnect) . "<br/>SQL: " . $sql, E_USER_ERROR);
        return $res;
    }

    private function sqlquery($sql) {
        $res = $this->sqlexecute($sql);

        $ar = array();
        while ($row = mysqli_fetch_array($res)) {
            $ar[] = $row;
        }

        return $ar;
    }

    private function toSql($text) {
        if (is_null($text)) {
            return "null";
        } else {
            return "'" . mysqli_real_escape_string($this->dbConnect, $text) . "'";
        }
    }

}
