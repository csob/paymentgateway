Changelog
---

### v1.6 (04/2016)

**Nová funkcionalita**

* TSGSMIPS-821 - eAPI 1.6
* TSGSMIPS-779 - Multilanguage support
* TSGSMIPS-479 - Multibrand s možností volby loga & barvy přes eAPI na úrovni transakce
* TSGSMIPS-340 - Trx dates extension
* TSGSMIPS-9 - Možnost vlastního generování klíčů
* TSGSMIPS-491 - Detailnější identifikace issuera
* TSGSMIPS-479 - Nastavení životnosti transakce přes eAPI

**Opravy chyb**

* [#57](https://github.com/csob/paymentgateway/issues/57) Endpoint `customer/info` nevrací `customerId`.
* [#85](https://github.com/csob/paymentgateway/issues/85) Operace `payment/recurrent` vrací http status 500
* [#95](https://github.com/csob/paymentgateway/issues/95) Chybějící středník ve WooCommerce pluginu
* [#109](https://github.com/csob/paymentgateway/issues/109) Chyba přesměrování po úspěšné platbě (Android)
* [#111](https://github.com/csob/paymentgateway/issues/111) Platba kartou pro testování opakované platby je vždy autorizována
* [#132](https://github.com/csob/paymentgateway/issues/132) Neznámý resultCode `999`
* [#144](https://github.com/csob/paymentgateway/issues/144) Reverse/refund šablony pro recurring platbu 

**Dokumentace**

* [#50](https://github.com/csob/paymentgateway/issues/50) Barvy platební brány
* [#78](https://github.com/csob/paymentgateway/issues/78) Nezdokumentovaný resultCode
* [#83](https://github.com/csob/paymentgateway/issues/83) Nepřesná ukázka requestu v dokumentaci
