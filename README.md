<p align="center">
  <img src="https://github.com/csob/paymentgateway/wiki/img/banner-new-9-2015.png/">
</p>

*please scroll down for [English](https://github.com/csob/paymentgateway#Čsob-online-payment-gateway)* :gb:

#Platební brána ČSOB

Platební brána ČSOB poskytuje služby akceptace karet Visa, Visa Electron, Visa VPAY, MasterCard, Maestro, DinersClub a mobilní peněženky MasterCard Mobile v online prostředí obchodů, služeb a mobilních aplikací. K využití služeb brány je nutné uzavření slouvy s bankou. 

##1. Platební brána ČSOB a objednávka služeb 

Produktové informace najdete na [https://platebnibrana.csob.cz](https://platebnibrana.csob.cz). Máte-li zájem o obchodní nabídku a uzavření smlouvy o online akceptaci karet s ČSOB, vyplňte, prosím, kontaktní formulář na stránce, nebo kdykoli volejte na 800 150 150.

##2. Specifikace API, jak integrovat a testovat

Platební brána má momentálně dvě rozhraní. Legacy API umožňuje přechod z již používané brány GP Webpay pro většinu obchodníků bez vývoje. Legacy API podporuje základní trasakční funkce, nepodporuje však funkce související se zjišťováním stavu transakcí a správou objednávek. Pokud ve Vašem e-shopu používáte SOAP rozhraní původní platební brány GP Webpay pro zjišťování stavů objednávek, implementujte, prosím, rovnou nové eAPI, protože Legacy API tuto funkci nemá. 
eAPI je zcela nové, moderní RESTful API pro komunikaci e-shopu s platební bránou a také základ pro rozvoj nových služeb. Kombinuje do jednoho interfacu jak transakční, tak obslužné funkce. 
Legacy API nebude již nadále rozvíjeno a proto doporučujeme v horizontu do léta 2016 vyvinout ve vašem e-shopu podporu eAPI. Jedině tak budete moci využívat všechny chystané funkce nové platební brány.

Než začnete bránu do vašeho shopu nebo mobilní aplikace eAPI integrovat, podívejte se, prosím, do [Wiki](https://github.com/csob/paymentgateway/wiki). Najdete zde kompletní dokumentaci k eAPI mezi e-shopem a bránou, návod jak simulovat různé transakční stavy a jaké používat testovací karty. 

Návod na migraci z platební brány ČSOB GP Webpay na novou platební bránu ČSOB najdete [zde](https://github.com/csob/paymentgateway/wiki/Přechod-z-již-použ%C3%ADvané-platebn%C3%AD-brány-GP-Webpay).

##3. Repozitář

V [repozitáři](https://github.com/csob/paymentgateway/tree/master/eshop-integration) najdete ukázkovou PHP a Java integraci, včetně plně funkčního pluginu pro WooCommerce. Z repozitáře si také stáhněte veřejný klíč brány, který budete potřebovat pro zabezpečení komunikace s platební bránou. Vlastní sadu klíčů si pak vygenerujte pomocí online nástroje, který jsme pro vás připravili na [https://platebnibrana.csob.cz/keygen/](https://platebnibrana.csob.cz/keygen/). Klíče pro iBránu (otevřené vývojové prostředí přístupné beze smlouvy) si můžete vygenerovat na [https://iplatebnibrana.csob.cz/keygen/](https://iplatebnibrana.csob.cz/keygen/)

##4. Problémy a pomoc při jejich řešení 

Nejrychleji najdete pomoc v sekci často kladených otázek. [Technická sekce] (https://github.com/csob/paymentgateway/wiki/Časté-technické-dotazy) je věnována zejména integraci, ve [funkční a obchodní části](https://github.com/csob/paymentgateway/wiki/Časté-funkčn%C3%AD-a-komerčn%C3%AD-dotazy) se dozvíte více o bráně a službách banky [link]. Větší detail k některým již dříve řešeným problémům najdete v issues. 

<p align="center">
  <img src="https://github.com/csob/paymentgateway/wiki/img/vbv.png/">
  <img src="https://github.com/csob/paymentgateway/wiki/img/mcseccode.png/">
</p>

#ČSOB Online Payment Gateway

The ČSOB payment gateway provides online cards acquiring services for e-shops, service providers and mobile apps. We currently support Visa, Visa Electron, Visa VPAY, MasterCard, Maestro, DinersClub and the MasterCard Mobile wallet. To use this gateway, you need to conclude a contract with the ČSOB. Please contact 800 150 150 (free from the Czech republic) for more commercial information in English. 

##1. API Documentation, how to Implement and Test your Shop Integration

Before starting the integration development, please have a look into the GitHub Wiki. The API documentation as well test cases and test cards are available here in the most up-to-date version. 

##2. Repository

Our GitHub repository contains example PHP and Java integration, including a fully working WooCommerce plugin. You can also download the public key of the gateway for securing the communication. You will also need to create your own set of keys using the online tool available on [https://platebnibrana.csob.cz/keygen/](https://platebnibrana.csob.cz/keygen/). 

##3. Issues, Troubleshooting and Support 

Please have a loot at the FAQ first. The [technical section](https://github.com/csob/paymentgateway/wiki/Technical-FAQ) is devoted mainly to integration, the [functional and commercial section](https://github.com/csob/paymentgateway/wiki/FAQ:-Features-and-Commercial-Issues) includes answers related mainly to the business functionality of the gateway. More detail to previously solved problems can be also found in the GitHub Issues. 

