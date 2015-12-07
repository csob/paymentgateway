This example will help you understand the payment gateway integration faster and in practical terms. The example will help you to create the cart, sign messages and verify the responses from the gateway. Should you have a problem with your implementation, please study this example before submitting an issue. Please feel free to refer to the exaple code when submitting an issue. Our support guys will be able to help you faster. 

Use this example for the integration environment to see how the payment gateway communicates. In order to talk to the gateway, please place your private key for message signing into directory 'keys'. The gateway's public key for response signature verification (mips_iplatebnibrana.csob.cz.pub) is already provided in the same directory. Then setup $merchantId and $privateKey in setup.php.

Please note this code is intended for demonstration purposes only and should not be deployed on production systems. When building your own production implementation, please make sure that the directory in which you store your private key for message signing is secured.


