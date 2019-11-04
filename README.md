<p align="center">
  <img src="https://github.com/csob/paymentgateway/wiki/img/mktg/banner-new-9-2015.png/">
</p>

# Platební brána ČSOB

Platební brána ČSOB poskytuje služby akceptace karet (Visa, Visa Electron, Visa VPAY, MasterCard, Maestro, DinersClub, mobilní peněženky MasterPass), Apple Pay, Mall Pay a platebních tlačítek ČSOB & Poštovní spořitelny v online prostředí obchodů, služeb a mobilních aplikací. K využití služeb brány je nutné uzavření slouvy s bankou. 

## 1. Platební brána ČSOB a objednávka služeb 

Produktové informace najdete na [https://platebnibrana.csob.cz](https://platebnibrana.csob.cz). Máte-li zájem o obchodní nabídku a uzavření smlouvy o online akceptaci karet s ČSOB, vyplňte, prosím, kontaktní formulář na stránce, nebo kdykoli volejte na 800 150 150.

## 2. Specifikace API, jak integrovat a testovat

Platební brána má momentálně dvě rozhraní. Hlavní rozhraní eAPI je moderní RESTful API pro komunikaci e-shopu s platební bránou a také základ pro rozvoj nových služeb. Kombinuje do jednoho interfacu jak transakční, tak obslužné funkce. Aktuálně je podporované ve verzích 1.0, 1.5, 1.6, 1.7 a 1.8. Doporučujeme při implementaci používat nejnovější verzi. Starší verze budou postupně uzavírány, počínaje verzí 1.0 na začátku roku 2020. 

Druhým rozhraním je Legacy API, které umožňuje přechod z již používané brány GP Webpay pro většinu obchodníků bez vývoje. Legacy API podporuje základní trasakční funkce, nepodporuje však funkce související se zjišťováním stavu transakcí a správou objednávek. Pokud ve Vašem e-shopu používáte SOAP rozhraní původní platební brány GP Webpay pro zjišťování stavů objednávek, implementujte, prosím, rovnou nové eAPI, protože Legacy API tuto funkci nemá. Legacy API nebude již nadále rozvíjeno a bude na počátku roku 2020 uzavřeno.

Než začnete bránu do vašeho shopu nebo mobilní aplikace eAPI integrovat, podívejte se, prosím, do [Wiki](https://github.com/csob/paymentgateway/wiki). Najdete zde kompletní dokumentaci k eAPI mezi e-shopem a bránou, návod jak simulovat různé transakční stavy a jaké používat testovací karty. 

Návod na migraci z platební brány ČSOB GP Webpay na novou platební bránu ČSOB najdete [zde](https://github.com/csob/paymentgateway/wiki/Přechod-z-již-použ%C3%ADvané-platebn%C3%AD-brány-GP-Webpay).

## 3. Repozitář

V [repozitáři](https://github.com/csob/paymentgateway/tree/master/Integration%20Examples) najdete ukázkovou PHP, Java a .NET integraci. Z repozitáře si také stáhněte veřejný klíč brány, který budete potřebovat pro zabezpečení komunikace s platební bránou. Vlastní sadu klíčů si pak vygenerujte pomocí online nástroje, který jsme pro vás připravili na [https://platebnibrana.csob.cz/keygen/](https://platebnibrana.csob.cz/keygen/). Klíče pro iBránu (otevřené vývojové prostředí přístupné beze smlouvy) si můžete vygenerovat na [https://iplatebnibrana.csob.cz/keygen/](https://iplatebnibrana.csob.cz/keygen/)

K dispozici jsou i řešení třetích stran, jejichž použití je pouze na Vašem uvážení např:

[ondrakoupil](https://github.com/ondrakoupil/csob)

[slevomat](https://github.com/slevomat/csob-gateway)

## 4. Problémy a pomoc při jejich řešení 

Nejrychleji najdete pomoc v sekci často kladených otázek. [Technická sekce](https://github.com/csob/paymentgateway/wiki/Časté-technické-dotazy) je věnována zejména integraci, ve [funkční a obchodní části](https://github.com/csob/paymentgateway/wiki/Časté-funkčn%C3%AD-a-komerčn%C3%AD-dotazy) se dozvíte více o bráně a službách banky. Větší detail k některým již dříve řešeným problémům najdete v issues.
