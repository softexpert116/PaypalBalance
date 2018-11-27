<?php
include 'Paypal.php';

$type = $_REQUEST['type'];
$username = $_REQUEST['username'];
$password = $_REQUEST['password'];
$signature = $_REQUEST['signature'];

$paypal = new Paypal($username, $password, $signature, 'live');

// $paypal = new Paypal('founders_api1.gmail.com', 'NXB39MYAK72P56M8', 'AX1jZmBfGCrOKA4XU7VLY-2dy4hUAXB-jGNK-9r86NE0M-xRj3AP0FQy', 'sandbox');
$response = $paypal->call($type);

$status = 400;
$balance = '';
$currency = '';
$date = '';
if ($response['ACK'] == 'Success') {
	$status = 200;
	$balance = $response['L_AMT0'];
	$currency = $response['L_CURRENCYCODE0'];
	$date = $response['TIMESTAMP'];
	$date = substr($date, 0, strpos($date,"T"));
}
echo json_encode(array('status' => $status, 'data' => array('username' => $username, 'balance' => $balance, 'currency' => $currency, 'date' => $date)));

?>