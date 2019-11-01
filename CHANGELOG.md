Changelog
---

### v1.8 (10/2019)

**Nová funkcionalita**

* TSGPMIPS-2302 - eAPI 1.8
* TSGPMIPS-2491 - platební metoda Mall Pay
* TSGPMIPS-2195 - platební metoda Apple Pay
* TSGPMIPS-2185 - platba na míru
* TSGPMIPS-2334 - podpis eAPI 1.8 pomocí SHA256
* TSGPMIPS-2486 - podpora mikrostavů pro platební metodu Mall Pay

**Opravy chyb**

* [#349](https://github.com/csob/paymentgateway/issues/349) PHP Example - špatné zacházení s parametrem closePayment.
* [#353](https://github.com/csob/paymentgateway/issues/353) Změny chybových hlášek při nefunkční zapamatované kartě

**Změny ve specifikaci eAPI 1.8 oproti v1.7**

* oproti eAPI 1.7 byly osamostatněny operace pro založení @shop platebních metod. Týká se to operací `applepay/init`, `masterpass/init`, `button/init` a `mallpay/init`. Původní `payment/init` slouží nově pouze pro založení platby na platební bráně. 
* operace `payment/oneclick/init` byla v eAPI 1.8 přejmenována na `oneclick/init`
* operace `payment/oneclick/start` byla v eAPI 1.8 přejmenována na `oneclick/start`
* operace `customer/info` byla v eAPI 1.8 přejmenována na `echo/customer`
* zrušeno pole `description` v operacích `payment/init` a `oneclick/init` (pozor, změní se tím i podpis požadavku)
* přidán nový povinný parametr `clientIp` v operaci `oneclick/init` (pozor, změní se tím i podpis požadavku)
* přidán nový parametr `statusDetail` v odpovědi na volání [`payment/*` operací](https://github.com/csob/paymentgateway/wiki/Z%C3%A1kladn%C3%AD-metody#n%C3%A1vratov%C3%A9-hodnoty-) obsahující detailní mikrostav transakce
* přidán číselník [detailní mikrostavy transakcí](https://github.com/csob/paymentgateway/wiki/Detailní-mikrostavy-transakcí)
* upraven [číselník návratových kódů](https://github.com/csob/paymentgateway/wiki/Vol%C3%A1n%C3%AD-rozhran%C3%AD-eAPI#%C4%8C%C3%ADseln%C3%ADk-n%C3%A1vratov%C3%BDch-k%C3%B3d%C5%AF-)
  * přidány nové návratové kódy `160` (Payment method disabled), `170` (Payment method unavailable), `190` (Payment method error)
  * návratový kód `220` (mpass@shop disabled) v eAPI 1.8 nahrazen kódem `160` (Payment method disabled)
  * návratový kód `260` (MasterPass server error) v eAPI 1.8 nahrazen kódem `190` (Payment method error)
  * návratový kód `400` (pt@shop (CSOB IB) disabled) v eAPI 1.8 nahrazen kódem `160` (Payment method disabled)
  * návratový kód `410` (pt@shop (ERA IB) disabled) v eAPI 1.8 nahrazen  kódem `160` (Payment method disabled)
  * návratový kód `420` (pt@shop (CSOB IB) unavailable) v eAPI 1.8 nahrazen kódem `170` (Payment method unavailable)
  * návratový kód `430` (pt@shop (ERA IB) unavailable) v eAPI 1.8 nahrazen  kódem `170` (Payment method unavailable)
  * přidán nový návratový kód `600` (Mall Pay payment declined in precheck)
  * přidán nový návratový kód `700` (Oneclick template not found)
  * přidán nový návratový kód `710` (Oneclick template payment expired)
  * přidán nový návratový kód `720` (Oneclick template card expired)
  * přidán nový návratový kód `730` (Oneclick template customer rejected)
  * přidán nový návratový kód `740` (Oneclick template payment reversed)


### v1.7 (01/2017)

**Nová funkcionalita**

* TSGPMIPS-1231 - eAPI 1.7
* TSGPMIPS-1327 - platební metoda MasterPass
* TSGPMIPS-1343 - platební tlačítko ČSOB 
* TSGPMIPS-1344 - platební tlačítko Poštovní spořitelny 
* TSGPMIPS-1549 - podpora EET

**Změny ve specifikaci eAPI 1.7 od vydání (bez funkčního dopadu)** 

* doplněn nový návratový kód `500` (EET rejected)
* doplněn chybějící paymentStatus v návratových parametrech operace `payment/button`
* doplněna podpora pro akceptaci nové měny `HRK` a nové lokalizace `HR` a `SI`
* upraven popis integrace `MasterPass.client.checkout` pro `mpass@shop`
* doplněn popis integrace `MasterPass.client.checkout` pro `mpass@shop` pro js callback
* doplněna podpora pro akceptaci nové měny `RON`, `NOK` a `SEK`


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
