<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <link rel="stylesheet" href="bootstrap.min.css">
</head>

<body>

<div class="container">
<h3>payment init test</h3>

<?php
require_once ('setup.php');
?>

<form role="form" name="myForm" id="myForm" action="init-result.php" method="post" class="form-horizontal">
  <div class="form-group">
    <label for="merchant_id" class="col-sm-2 control-label">Merchant ID</label>
    <div class="col-sm-4">
		<select class="form-control text-right" name="merchant_id" id="merchant_id">
        <option value="<?= htmlspecialchars($merchantId, ENT_QUOTES); ?>"><?= htmlspecialchars($merchantId, ENT_QUOTES); ?></option>
      </select>
    </div>
  </div>
  <div class="form-group">
    <label for="order_no" class="col-sm-2 control-label">Order No</label>
    <div class="col-sm-4">
		<input class="form-control text-right" name="order_no" id="order_no" value="123456"/>
    </div>
  </div>
  <div class="form-group">
    <label for="total_amount" class="col-sm-2 control-label">Total amount (CZK)</label>
    <div class="col-sm-4">
    <input class="form-control text-right" name="total_amount" id="total_amount" value="1"/>
    </div>
  </div>
  <div class="form-group">
    <label for="shipping_amount" class="col-sm-2 control-label">Shipping amount (CZK)</label>
    <div class="col-sm-4">
    <input class="form-control text-right" name="shipping_amount" id="shipping_amount" value="0"/>
    </div>
  </div>
  <div class="form-group">
    <label for="return_url" class="col-sm-2 control-label">Return URL</label>
    <div class="col-sm-4">
    <input class="form-control text-right" name="return_url" id="return_url" value="https://vasobchod.cz/gateway-return"/>
    </div>
  </div>
  <div class="form-group">
    <label for="goods_desc" class="col-sm-2 control-label">Goods description</label>
    <div class="col-sm-4">
    <input class="form-control text-right" name="goods_desc" id="goods_desc" value="Lenovo ThinkPad Edge E540"/>
    </div>
  </div>
  <div class="form-group">
    <label for="description" class="col-sm-2 control-label">Description</label>
    <div class="col-sm-4">
    <input class="form-control text-right" name="description" id="description" value="Nákup na vasobchod.cz (Lenovo ThinkPad Edge E540, Doprava PPL)"/>
    </div>
  </div>
  <div class="form-group">
    <label for="customer ID" class="col-sm-2 control-label">Customer ID</label>
    <div class="col-sm-4">
    <input class="form-control text-right" name="customer_id" id="customer_id" value="1234"/>
    </div>
  </div>

  <div class="form-group">
	<label for="submit" class="col-sm-2 control-label">&nbsp;</label>
    <div class="col-sm-2">
	  <input id="submit" type="submit" class="form-control btn btn-primary" value="Test">
    </div>
  </div>

</form>

</div>


</body>
</html>