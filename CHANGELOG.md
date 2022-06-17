Changelog
---

### v1.9 (06/2022)

**New features**

* EPAPG-19 - eAPI 1.9
* EPAPG-25 - Extends support for payment authentication using 3D Secure 2
* EPAPG-24 - Google Pay payment method
* EPAPG-14 - NEJsplátka loan payment method
* EPAPG-228 - Revenue reporting to ESR is no longer supported

**Bug fixes**

* [#562](https://github.com/csob/paymentgateway/issues/562) return codes oneclick/init vs. oneclick/echo
* [#570](https://github.com/csob/paymentgateway/issues/570) transaction expiration (`ttlSec`)
* [#615](https://github.com/csob/paymentgateway/issues/615) validation of `vatin` parameter for mallpay changed from 10 to 12 chars

**Changes in eAPI 1.9 specification compared to v1.8**

* card payment on the payment gateway
  * parameters `payOperation`, `payMethod` and `closePayment` are in `payment/init` operation in eAPI 1.9 optional
  * `payMethod` parameter may contain a new value `card#LVP` (strong customer authentication exception for low value payments)
  * added new parameters `customer` and `order` in `payment/init` operation for providing additional purchase data
  * values in `language` parameter in `payment/init` operation are newly ISO 639-1 language codes  (in older eAPI version country codes were specified)
  * added `actions` structure in `payment/status` response
* OneClick payment method
  * added `maskClnRp` extension to `oneclick/echo` operation
  * parameter `clientIp` in `oneclick/init` operation is in eAPI 1.9 newly mandatory in case `clientInitiated = true`
  * added parameter `closePayment` in `oneclick/init` operation (in older eAPI versions the value was taken from OneClick template)
  * added new parameters `returnUrl` and `returnMethod` in `oneclick/init` operation
  * added new parameters `customer` and `order` in `oneclick/init` operation for providing additional purchase data
  * added new parameters `clientInitiated` and `sdkUsed` in `oneclick/init` operation
  * operation `oneclick/start` renamed to `oneclick/process`, added support for data transfer for payment authentication (`fingerprint` and `actions` structures)
* Apple Pay payment method
  * added new operation `applepay/echo`
  * parameter `closePayment` is in `applepay/init` operation in eAPI 1.9 newly optional
  * parameter `payload` in eAPI 1.9 moved to `applepay/init` operation (in eAPI 1.8 was `payload` in `applepay/start` operation)
  * added new parameters `returnUrl` and `returnMethod` in operation `applepay/init`
  * added new parameters `customer` and `order` in operation `applepay/init` for providing additional purchase data
  * added new parameter `sdkUsed` in operation `applepay/init`
  * operation `applepay/start` renamed to `applepay/process`, added support for data transfer for payment authentication (`fingerprint` and `actions` structures)
* Google Pay payment method
  * added new operations `googlepay/echo`, `googlepay/init` and `googlepay/process`
* ČSOB Payment Button
  * parameter `brand` in `button/init` operation is now optional, allowed value is `csob` (value `era` is not supported)
* mallpay payment method
  * billing address is in `mallpay/init` operation newly optional
* NEJsplátka loan payment method
  * added new operations `loan/init`, `loan/logistics`, `loan/cancel` and `loan/refund`
* removed `masterpass/*` operations from eAPI 1.9 (after MasterPass payment method termination to 31.10.2020 the eAPI 1.7 and 1.8 masterpass operations return appropriate result codes)
* terminated support of `eetV3` extension for eAPI 1.9
* changes in return code list (`resultCode`)
  * added new result code `200` Duplicate purchaseId
  * added new result code `750` Cardholder account closed
  * result codes `230`, `240`, `250` and `270` (relevant for MasterPass payment method) were annotated as deprecated


### v1.8 (10/2019)

**New features**

* TSGPMIPS-2302 - eAPI 1.8
* TSGPMIPS-2334 - signature in eAPI 1.8 uses SHA256
* TSGPMIPS-2185 - custom payment
* TSGPMIPS-2195 - Apple Pay payment method
* TSGPMIPS-2491 - Mall Pay payment method
* TSGPMIPS-2486 - microstates support for Mall Pay payment method

**Bug fixes**

* [#349](https://github.com/csob/paymentgateway/issues/349) PHP Example - wrong handling of closePayment parameter.
* [#353](https://github.com/csob/paymentgateway/issues/353) Změny chybových hlášek při nefunkční zapamatované kartě

**Changes in eAPI 1.8 specification compared to v1.7**

* in contrast to eAPI 1.7 new init operations were specified to create @shop payment methods. See `applepay/init`, `masterpass/init`, `button/init` and `mallpay/init` operations. Original `payment/init` operation may be used only for card@bank payment method on the payment gateway. 
  * `payment/oneclick/init` operation was renamed in eAPI 1.8 to `oneclick/init`
  * `payment/oneclick/start` operation was renamed in eAPI 1.8 to `oneclick/start`
  * `customer/info` operation operation was renamed in eAPI 1.8 to `echo/customer`
* parameter `description` in `payment/init` and `oneclick/init` operations was removed (this has an impact to request signature)
* added new mandatory parameter `clientIp` in `oneclick/init` operation (this has an impact to request signature)
* added new parameter `statusDetail` in response to [`payment/*` operations](https://github.com/csob/paymentgateway/wiki/Basic-methods#payment-status-operation), contains transaction microstate
* added [detailed status list](https://github.com/csob/paymentgateway/wiki/Payment-lifecycle#status-detail)
* updated [return code list](https://github.com/csob/paymentgateway/wiki/API-Integration#return-code-list-)
  * added new generic result code `160` (Payment method disabled), replacing the following codes:
      * removed result code `220` (mpass@shop disabled), in eAPI 1.8 replaced by `160` code
      * removed result code `400` (pt@shop (CSOB IB) disabled), in eAPI 1.8 replaced by `160` code
      * removed result code `410` (pt@shop (ERA IB) disabled), in eAPI 1.8 replaced by `160` code
  * added new generic result code `170` (Payment method unavailable), replacing the following codes:
      * result code `420` removed (pt@shop (CSOB IB) unavailable), in eAPI 1.8 replaced by `170` code
      * result code `430` removed (pt@shop (ERA IB) unavailable), in eAPI 1.8 replaced by `170` code
  * added new generic result code `190` (Payment method error), replacing the following codes:
      * removed result code `260` (MasterPass server error), in eAPI 1.8 replaced by `190` code
  * added new result code `600` (Mall Pay payment declined in precheck)
  * added new result code `700` (Oneclick template not found)
  * added new result code `710` (Oneclick template payment expired)
  * added new result code `720` (Oneclick template card expired)
  * added new result code `730` (Oneclick template customer rejected)
  * added new result code `740` (Oneclick template payment reversed)


### v1.7 (01/2017)

**New features**

* TSGPMIPS-1231 - eAPI 1.7
* TSGPMIPS-1327 - MasterPass payment method
* TSGPMIPS-1343 - ČSOB payment button
* TSGPMIPS-1344 - Poštovní spořitelna payment button 
* TSGPMIPS-1549 - ESR revenue support

**Changes in eAPI 1.7 specification (no impact to existing functionality)** 

* added new result code `500` (EET rejected)
* added missing paymentStatus in `payment/button` operation response
* added support for accepting a new `HRK` currency, added new localizations `HR` and `SI`
* updated description of integration of `MasterPass.client.checkout` for `mpass@shop`
* added description of integration of `MasterPass.client.checkout` for  `mpass@shop` (js callback)
* new currencies `RON`, `NOK` and `SEK` accepted on the payment gateway


### v1.6 (04/2016)

**New features**

* TSGSMIPS-821 - eAPI 1.6
* TSGSMIPS-779 - Multilanguage support
* TSGSMIPS-479 - Custom Multibrand s možností volby loga & barvy přes eAPI na úrovni transakce
* TSGSMIPS-340 - Trx dates extension
* TSGSMIPS-9 - Improved keys generating
* TSGSMIPS-491 - Detailed issuer identification
* TSGSMIPS-479 - Setup of transaction lifecycle via eAPI

**Bug fixes**

* [#57](https://github.com/csob/paymentgateway/issues/57) Endpoint `customer/info` does not return `customerId`.
* [#85](https://github.com/csob/paymentgateway/issues/85) Operation `payment/recurrent` returns http status 500
* [#95](https://github.com/csob/paymentgateway/issues/95) Missing semicolon in WooCommerce plugin
* [#109](https://github.com/csob/paymentgateway/issues/109) Error in redirect after successful payment (Android)
* [#111](https://github.com/csob/paymentgateway/issues/111) Card payment used for testing reccuring payment is always authorized
* [#132](https://github.com/csob/paymentgateway/issues/132) Unknown resultCode `999`
* [#144](https://github.com/csob/paymentgateway/issues/144) Reverse/refund of template for recurring payment 

**Documentation**

* [#50](https://github.com/csob/paymentgateway/issues/50) The custom color scheme on the payment gateway
* [#78](https://github.com/csob/paymentgateway/issues/78) Undocumented resultCode
* [#83](https://github.com/csob/paymentgateway/issues/83) Inaccurate example of request in docs