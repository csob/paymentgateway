<!doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" href="bootstrap.min.css">
    </head>

    <body>

        <div class="container">
            <h3>payment init JSON test</h3>

            <p>payment/init data - <strong>signature and dttm</strong> added automatically by application</p>

            <?php require_once ('setup.php'); ?>

            <form role="form" name="myForm" id="myForm" action="init-json-result.php" method="post" class="form-horizontal">
                <div class="form-group">
                    <div class="col-sm-10">
                        <textarea style="font-family:monospace;" name="initdata" id="initdata" rows="30" cols="150">
{
  "merchantId":"<?php echo htmlspecialchars($merchantId); ?>",
  "orderNo":"554822",
  "payOperation":"payment",
  "payMethod":"card",
  "totalAmount":123400,
  "currency":"CZK",
  "closePayment": true,
  "returnUrl":"https://store.example.com/return",
  "returnMethod":"POST",
  "cart":[
    {
      "name": "Wireless headphones",
      "quantity": 1,
      "amount": 123400
    },
    {
      "name": "Shipping",
      "quantity": 1,
      "amount": 0,
      "description": "DPL"
    }
  ],
  "merchantData":"c29tZS1iYXNlNjQtZW5jb2RlZC1tZXJjaGFudC1kYXRh",
  "language":"cs"
}
</textarea>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-2">
                        <input id="submit" type="submit" class="form-control btn btn-primary" value="Test">
                    </div>
                </div>

            </form>

        </div>

    </body>
</html>