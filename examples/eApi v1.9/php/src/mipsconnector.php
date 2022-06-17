<?php
namespace Monetplus;
use GuzzleHttp\Middleware;
use Monolog\Logger;
use GuzzleHttp\MessageFormatter;
use Monolog\Handler\StreamHandler;

require dirname(__DIR__) . '/vendor/autoload.php';
use GuzzleHttp\Client;


class MipsConnector
{
  private Client $client;

  private String $baseUrl;
  public function __construct(String $baseUrl)
  {  
    $this->baseUrl = $baseUrl;
    $stack = \GuzzleHttp\HandlerStack::create();
    $logger = new Logger('Logger');
    $logger->pushHandler(new StreamHandler('php://stdout', Logger::DEBUG));
    $stack->push(
      Middleware::log(
      $logger,
      new MessageFormatter('REQUEST:{req_body}   -   RESPONSE:{code} : {res_body}')
    )
    );

    $this->client = new Client([
      'base_uri' => $baseUrl,
      'timeout' => 20.0,
      'headers' => ['Content-Type' => 'application/json'],
      'handler' => $stack
    ]);

  }
  function connector()
  {
    return $this->client;
  }
  function baseUrl() : String {
    return $this->baseUrl;
  }
}

?>