<?php

class Logger {

    private $log;

    public function __construct() {
        $this->log = dirname(__FILE__) . "/log/paymentLog.log";
    }

    function write($string) {
        $line = "----- " . date('r') . " ----- " . $string . "\n";
        file_put_contents($this->log, $line, FILE_APPEND | LOCK_EX);
    }

}
