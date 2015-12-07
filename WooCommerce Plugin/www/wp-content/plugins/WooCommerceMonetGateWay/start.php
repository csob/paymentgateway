<?php

/*
 * Plugin Name: Payment gateway plugin for Woo Commerce e-shop
 * Plugin URI: www.monet.cz
 * Description: e-commerce plugin for CSOB Payment Gateway (https://platebnibrana.csob.cz) implemented as extension of Woo Commerce e-shop
 * Version: 1.00
 * Author: MONET+, a.s.
 * Author URI: www.monetplus.cz
 * License: GNU General Public License v2 or later
 * License URI: http://www.gnu.org/licenses/gpl-2.0.html
 */
add_action ( 'plugins_loaded', 'woocommerce_monet_pay_init', 0 );

register_activation_hook ( __FILE__, 'install_transaction_db_table' );
function install_transaction_db_table() {
	global $wpdb;
	$sql = "DROP TABLE IF EXISTS monetTransaction";
	$wpdb->query ( $sql );
	
	$sql = "
	        CREATE TABLE IF NOT EXISTS monetTransaction (
	        	orderNumber varchar(10) DEFAULT NULL,
	          	payId varchar(20) DEFAULT NULL,
	          	paymentStatus int(11) NOT NULL,
	          	created datetime NOT NULL,
	          	updated datetime DEFAULT NULL,
		      	paymentResponse datetime NOT NULL,
		      	authCode varchar(30) DEFAULT NULL,
			 	cart varchar (1000) DEFAULT NULL,
	          	PRIMARY KEY (orderNumber)
	        );
	    ";
	
	$wpdb->query ( $sql );
}
function woocommerce_monet_pay_init() {
	require_once (ABSPATH . "/monetWebPay/MonetWebPay.php");
	
	if (! class_exists ( 'WC_Payment_Gateway' ))
		return;
	
	$log = new Logger();
	class WC_Monet_Payment_Gateway extends WC_Payment_Gateway {
		
		public function __construct() {
			
			$this->init_settings ();
			$this->id = 'MonetWebPay';
			$this->medthod_title = $this->id; // MonetWebPay
			$this->icon = get_bloginfo ( 'url' ) . "/monetWebPay/cards.png";
			$this->urlGate = isset ( $this->settings ['urlGate'] ) ? $this->settings ['urlGate'] : '';
			$this->title = $this->get_option ( 'title' );
			$this->description = $this->get_option ( 'description' );
			$this->urlGate = $this->get_option ( 'urlGate' );
			$this->merchantId = $this->get_option ( 'merchantId' );
			$this->publicKey = $this->get_option ( 'publicKey' );
			$this->privateKey = $this->get_option ( 'privateKey' );
			$this->privateKeyPassword = $this->get_option ( 'privateKeyPassword' );
			$this->moneyTransfer = $this->get_option ( 'moneyTransfer' );
			$this->returnMethodPOST = $this->get_option( 'returnMethodPOST' );
			$this->firstCartItemDesc = $this->get_option ( 'firstCartItemDesc' );
			$this->secondCartItemDesc = $this->get_option ( 'secondCartItemDesc' );
			$this->msg ['message'] = "";
			$this->msg ['class'] = "";
			
			$monetWebPay = new MonetWebPay ( $this->prepareDatabaseConfig () );
			
			$this->monetWebPay = $monetWebPay;
			
			add_action ( 'woocommerce_update_options_payment_gateways_' . $this->id, array (
					&$this,
					'process_admin_options' 
			) );
			$this->init_form_fields ();
			
			add_action ( 'woocommerce_receipt_' . $this->id, array (
					&$this,
					'receipt_page' 
			) );
		}
		public function prepareDatabaseConfig() {
			global $wpdb;
			$databaseConfig = new DatabaseConfig ();
			$databaseConfig->host = $wpdb->dbhost;
			$databaseConfig->name = $wpdb->dbname;
			$databaseConfig->user = $wpdb->dbuser;
			$databaseConfig->password = $wpdb->dbpassword;
			
			return $databaseConfig;
		}
		
		/**
		 * Admin Panel Options
		 */
		public function admin_options() {
			echo '<h3>' . 'Monet Payment Gateway' . '</h3>';
			echo '<table class="form-table">';
			$this->generate_settings_html ();
			echo '</table>';
		}
		/**
		 * There are no payment fields for EBS, but we want to show the description if set.
		 */
		function payment_fields() {
			if ($this->description)
				echo wpautop ( wptexturize ( $this->description ) );
		}
		
		/**
		 * Receipt Page
		 */
		public function receipt_page($order) {
			// echo sprintf ( '<p>' . __ ( 'Děkujeme za Vaši objednávku. Nyní se provede přesměrování na platební bránu.', 'index' ) . '</p>' );
			echo $this->save_order_process ( $order );
		}

		public function save_order_process($order_id) {
			global $woocommerce;
			$order = new WC_Order($order_id);
			$this->monetWebPay->log->write('orderNumber ' .  $order->get_order_number());

			$partsOforderNumber = $this->exploderOrderNumber($order->get_order_number());
			$this->monetWebPay->log->write('parts of orderNumber ' .  $partsOforderNumber[0] . ', '  .  $partsOforderNumber[1]);
			$orderNo = $order->get_order_number();
			$location = $this->get_return_url ( $order );
			$customer = $woocommerce->customer;
			$dttm = (new DateTime ())->format ( "YmdHis" );
			$data;
			$returnUrl = plugins_url ( 'returnUrl.php?orderNumber=' . $order_id, __FILE__ ); 
			
			$this->monetWebPay->log->write('Checking order ' .  $orderNo . ", orderId " . $order_id);
			$row = $this->monetWebPay->selectTransaction ( $partsOforderNumber[0] ); // kontrola jestli existuje objednavka
			$this->monetWebPay->log->write('after select');

			$cart = createCartData($woocommerce->cart, $order->get_total(), $this->firstCartItemDesc, $this->secondCartItemDesc);
			$this->monetWebPay->log->write('cart created');
			$paymentId = $row['payId'];
			$paymentStatus = $row['paymentStatus'];
			$this->monetWebPay->log->write('loaded paymentStatus: ' . $paymentStatus . ' PayId: ' . $paymentId );

			// pokud neni dosud platba inicializovana anebo byla zrusena nebo zamitnuta (payId null) anebo se zmenil kosik, provedeme payment/init
			if (is_null($paymentId) || (!is_null($row['cart']) && ($row['cart'] !== json_encode($cart)))) { 

				$this->monetWebPay->log->write('payment not inicialized OR payment cancelled or declined OR detected cart changes');

				$urlGate = $this->urlGate . NativeApiMethod::$init;
				$this->monetWebPay->log->write('payment/init, url: ' . $urlGate);

				if(!is_null($row['paymentStatus']) && (($row['paymentStatus'] == PaymentStatus::$canceled) || ($row['paymentStatus'] == PaymentStatus::$declined))) {
					$this->monetWebPay->log->write('payment cancelled or declined, setting up cart from db: ' . $row['cart']);
					$cart = json_decode($row['cart'], true);
				}
				
				$data = createPaymentInitData ( $this->merchantId, $partsOforderNumber[0], $dttm, $order->get_total(), $returnUrl, $cart, "Objednavka " . $order->get_order_number(),
						$order->get_user_id(), $this->privateKey, $this->privateKeyPassword, $this->moneyTransfer, null, $this->returnMethodPOST );
				
				$this->monetWebPay->log->write('payment/init data: ' . json_encode ( $data ));

				$ch = curl_init ( $urlGate );
				curl_setopt ( $ch, CURLOPT_CUSTOMREQUEST, "POST" );
				curl_setopt ( $ch, CURLOPT_POSTFIELDS, json_encode ( $data ) );
				curl_setopt ( $ch, CURLOPT_RETURNTRANSFER, true );
				curl_setopt ( $ch, CURLOPT_SSL_VERIFYPEER, false);
				curl_setopt ( $ch, CURLOPT_HTTPHEADER, array (
						'Content-Type: application/json',
						'Accept: application/json;charset=UTF-8' 
				) );
				
				$result = curl_exec ( $ch );

				if(curl_errno($ch)) {
					$this->msg ['message'] = 'Inicilizace platby na platební bráně se nezdařila, důvod: ' . curl_error($ch);
					$this->msg ['class'] = 'error';
					wc_add_notice ( __ ( $this->msg ['message'], 'monet' ) . $error_message, 'error' );
					return;
				}

				curl_close($ch);

				$this->monetWebPay->log->write('payment/init result: ' . $result);

				$result_array = json_decode ( $result, true );
				if(is_null($result_array ['resultCode'])) {
					$this->msg ['message'] = 'Přesměrování na platební bránu se nezdařilo';
					$this->msg ['class'] = 'error';
					wc_add_notice ( __ ( $this->msg ['message'], 'monet' ) . $error_message, 'error' );
					return;
				}

				$response = $this->prepareResponse($result_array);
				if ($this->monetWebPay->verifyPaymentInitSignature($response, $this->publicKey, "payment/init verify") == false) {
					$this->msg ['message'] = 'Inicilizace platby na platební bráně se nezdařila, nepodařilo se ověřit podpis odpovědi';
					$this->msg ['class'] = 'error';
					wc_add_notice ( __ ( $this->msg ['message'], 'monet' ) . $error_message, 'error' );
					return;
				}

				if ($result_array ['resultCode'] != '0') {
					$this->msg ['message'] = 'Iniclizace platby na platební bráně se nezdařila, důvod: ' . $result_array ['resultMessage'];
					$this->msg ['class'] = 'error';
					wc_add_notice ( __ ( $this->msg ['message'], 'monet' ) . $error_message, 'error' );
					return;
				}
				
				$paymentId = $result_array ['payId'];
				if(is_null($row['paymentStatus'])) {
					$this->monetWebPay->insertTransaction($response, $partsOforderNumber[0], $data['cart']);
				} else {
					$this->monetWebPay->updateTransaction($partsOforderNumber[0], $response, $data['cart']);
				}
			}
			
			$dttm = (new DateTime ())->format ( "YmdHis" );
			$urlProccessPart = createPaymentProcessUrl( $this->merchantId, $paymentId, $dttm, $this->privateKey, $this->privateKeyPassword);
			$processUrlGate = $this->urlGate . NativeApiMethod::$process . $urlProccessPart;
			$this->monetWebPay->log->write('executing payment/process, url: ' . $processUrlGate);

			header ( 'Location: ' . $processUrlGate );
		}		
				
		public function init_form_fields() {
			$this->form_fields = array (
					'merchantId' => array (
							'title' => 'ID obchodníka',
							'type' => 'text',
							'required'  => true 
					),
					'urlGate' => array (
							'title' => 'Adresa brány',
							'type' => 'text',
							'required'  => true 
					),
					'publicKey' => array (
							'title' => 'Veřejný klíč',
							'type' => 'text',
							'required'  => true 
					),
					'privateKey' => array (
							'title' => 'Soubor privátního klíče',
							'type' => 'text',
							'required'  => true 
					),
					'privateKeyPassword' => array (
							'title' => 'Heslo privátního klíče',
							'type' => 'text' 
					),
					'moneyTransfer' => array (
							'title' => 'Převod peněz',
							'type' => 'select',
							'options' => array (
									'1' => 'Převést ihned',
									'0' => 'Převést až po zařazení do zúčtování' 
							) 
					),
					'returnMethodPOST' => array (
							'title' => 'Návratová metoda POST',
							'type' => 'checkbox',
							'description' => 'V případě odškrnutí bude návratová hodnota GET',
                			'default' => 'yes'
					),
					'enabled' => array (
							'title' => __ ( 'Povolit/Zakázat', 'monet' ),
							'type' => 'checkbox',
							'label' => 'Zapnuto',
							'default' => 'yes' 
					),
					'title' => array (
							'title' => 'Titulek',
							'type' => 'text',
							'desc_tip' => true 
					),
					'description' => array (
							'title' => 'Popis',
							'type' => 'textarea',
							'desc_tip' => true 
					),
					'firstCartItemDesc' => array (
							'title' => 'Název první položky košíku',
							'type' => 'text',
                			'placeholder' => 'Nákup v obchodě ...',
							'required'  => true 
					),
					'secondCartItemDesc' => array (
							'title' => 'Název druhé položky košíku',
							'type' => 'text',
                			'placeholder' => 'Poštovné',
							'required'  => true 

					) 
			);
		}

		public function process_payment($order_id) {
			$order = new WC_Order ( $order_id );
			if (version_compare ( WOOCOMMERCE_VERSION, '2.1.0', '<=' )) {
				$return = array (
						'result' => 'success',
						'redirect' => get_permalink ( get_option ( 'woocommerce_pay_page_id' ) ) 
				);
			} else {
				$return = array (
						'result' => 'success',
						'redirect' => $order->get_checkout_payment_url ( true ) 
				);
			}
			return $return;
		}

		public function showMessage() {
			return '<div class="box ' . $this->msg ['class'] . '-box">' . $this->msg ['message'] . '</div>' . $content;
		}
		
		public function prepareResponse($result_array = null) {			
			$response = new Response();
			
			if($result_array != null) {
				$response->payId = $result_array ['payId'];
				$response->dttm = $result_array ['dttm'];
				$response->resultCode = $result_array ['resultCode'];
				$response->resultMessage = $result_array ['resultMessage'];
				$response->paymentStatus = $result_array ['paymentStatus'];
				$response->authCode = $result_array ['authCode'];
				$response->merchantData = $result_array ['merchantData'];
				$response->signature = $result_array ['signature'];
			} else {
				$response->payId = (is_null($_GET ['payId'])) ? $_POST ['payId'] : $_GET ['payId'];
				$response->dttm =(is_null( $_GET ['dttm'])) ? $_POST ['dttm'] : $_GET ['dttm'];
				$response->resultCode = (is_null($_GET ['resultCode'])) ? $_POST ['resultCode'] : $_GET ['resultCode'];
				$response->resultMessage = (is_null($_GET ['resultMessage'])) ? $_POST ['resultMessage'] : $_GET ['resultMessage'];
				$response->paymentStatus = (is_null($_GET ['paymentStatus'])) ? $_POST ['paymentStatus'] : $_GET ['paymentStatus'];
				$response->authCode = (is_null($_GET ['authCode'])) ? $_POST ['authCode'] : $_GET ['authCode'];
				$response->merchantData = (is_null($_GET ['merchantData'])) ? $_POST ['merchantData'] : $_GET ['merchantData'];
				$response->signature = (is_null($_GET ['signature'])) ? $_POST ['signature'] : $_GET ['signature'];
			}
			
			return $response;
		}
		
		public function exploderOrderNumber($orderNumber) {
			return explode ( '.', $orderNumber );
		}
		
		public function processOrder($order_id) {
			global $woocommerce;
			$order = new WC_Order ( $order_id );
			$partsOforderNumber = $this->exploderOrderNumber($order->get_order_number());
			$response = $this->prepareResponse();
					
			$this->monetWebPay->log->write("Processing response, payId: " . $response->payId . " dttm: " . $response->dttm . " resultCode: " . $response->resultCode . " resultMessage: " . $response->resultMessage . " authCode: " . $response->authCode . " signature: " . $response->signature );
			if ($this->monetWebPay->verifyPaymentResponseSignature($response, $this->publicKey, "payment response verify") == false) {
				$this->monetWebPay->log->write('Response signature verification failed for payId ' . $response->payId);
				$redirect = $order->get_checkout_payment_url ( true );
				$this->msg ['message'] = 'Nepodařilo se ověřit podpis odpovědi';
				$this->msg ['class'] = 'error';
				wc_add_notice ( __ ( $this->msg ['message'], 'monet' ) . $error_message, 'error' );
				return;
			}

			if ($response->resultCode != 0) {
				$this->monetWebPay->log->write('Response resultCode is ' . $response->resultCode . " [" . $response->resultMessage . "] for payId " . $response->payId);
			}
						
			if ($response->paymentStatus == PaymentStatus::$approved) {
				
				$this->monetWebPay->log->write('Received approved status for payId: ' . $response->payId);
				$this->monetWebPay->updateTransactionStatus ( $partsOforderNumber[0], $response);
				$this->msg ['class'] = 'woocommerce_message';
				$this->msg ['message'] = sprintf ( __ ( 'Platba byla zpracována a čeká v bance na zařazení do zúčtovaní. Číslo objednávky: %s', 'monet' ), $partsOforderNumber [0] );
				$order->add_order_note( $this->msg ['message'] );
				$woocommerce->cart->empty_cart ();
				$order->payment_complete();
				$order->update_status ( 'on-hold' );							

			} else if ($response->paymentStatus == PaymentStatus::$canceled) {
				
				$this->monetWebPay->log->write('Received cancelled status for payId: ' . $response->payId);
				$this->monetWebPay->clearTransaction($partsOforderNumber[0], $response);
				$this->msg ['message'] = sprintf ( __ ( 'Platba byla na platební bráně zrušena zákazníkem. Číslo objednávky: %s', 'monet' ), $partsOforderNumber[0] );
				$this->msg ['class'] = 'woocommerce_message woocommerce_message_info';
				$order->add_order_note ( $this->msg ['message'] );
				$order->update_status ( 'failed' );							
				wc_add_notice ( __ ( $this->msg ['message'], 'monet' ) . $error_message, 'error' );

			} else if ($response->paymentStatus == PaymentStatus::$declined) {

				$this->monetWebPay->log->write('Received declined status for payId: ' . $response->payId);
				$this->monetWebPay->clearTransaction($partsOforderNumber[0], $response);
				$this->msg ['message'] = 'Platba byla na platební bráně zamítnuta. Číslo objednávky: ' . $partsOforderNumber[0];
				$this->msg ['class'] = 'woocommerce_message woocommerce_message_info';
				$order->add_order_note ( sprintf ( $this->msg ['message'] ) );
				$order->update_status ( 'failed' );
				wc_add_notice ( __ ( $this->msg ['message'], 'monet' ) . $error_message, 'error' );

			} else if ($response->paymentStatus == PaymentStatus::$toClearing) {

				$this->monetWebPay->log->write('Received to_clearing status for payId: ' . $response->payId);
				$this->monetWebPay->updateTransactionStatus ( $partsOforderNumber[0], $response);
				$this->msg ['class'] = 'woocommerce_message woocommerce_message_info';
				$this->msg ['message'] = sprintf ( __ ( 'Platba byla zpracována a je v bance zařazena do zúčtovaní. Číslo objednavky: %s', 'monet' ), $partsOforderNumber[0] );
				$order->add_order_note ( $this->msg ['message'] );
				$woocommerce->cart->empty_cart ();
				$order->payment_complete();

			}
            
			$location = $this->get_return_url ( $order );
			wp_safe_redirect ( $location );
			exit ();
		}
	}

	function woocommerce_add_monetwebpay_gateway($methods) {
		$methods [] = 'WC_Monet_Payment_Gateway';
		return $methods;
	}
	
	add_filter ( 'woocommerce_payment_gateways', 'woocommerce_add_monetwebpay_gateway' );
}

?>