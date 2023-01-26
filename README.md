# ČSOB Online Payment Gateway

The ČSOB payment gateway provides online cards acquiring services for e-shops, service providers and mobile apps. We currently support card payments (Visa, Visa Electron, Visa V Pay, MasterCard, Maestro), Apple Pay, Google Pay, Skip Pay (originally mallpay) and ČSOB Payment Button. 
To use the Payment Gateway, you need to conclude a contract with the ČSOB. Please contact 800 150 150 (free from the Czech republic) for more commercial information in English. 

Acceptance of Diners Club payment cards was terminated by 30.9.2022.

By 6 October 2022 mallpay rebranded to Skip Pay. Except the name everything remains the same. Only in the eAPI section in the wiki and in API code mallpay is still being used (operations and parameters). Methods for mallpay (Skip Pay) remain functional.

Due to Croatia's transition to the euro as its official currency, it will not be possible to make payments in Croatian kuna (HRK) from 1 January 2023. Any refunds of transactions made in the original HRK currency will only be made in full in EUR.

The product NEJsplátka consumer loan comparator is not currently active.

## 1. API Documentation, how to Implement and Test your Shop Integration

Before starting the integration development, please have a look into the GitHub Wiki. The API documentation as well test cases and test cards are available here in the most up-to-date version. Older versions of API will be [terminated](https://github.com/csob/paymentgateway/wiki/API-Sunset).

## 2. Repository

Our GitHub repository contains example PHP, Java and .NET integration. You can also download the public key of the gateway for securing the communication. You will also need to create your own set of keys using the online tool available on [https://platebnibrana.csob.cz/keygen/](https://platebnibrana.csob.cz/keygen/). 

## 3. Issues, Troubleshooting and Support 

Please have a loot at the FAQ first. The [technical section](https://github.com/csob/paymentgateway/wiki/Technical-FAQ) is devoted mainly to integration, the [functional and commercial section](https://github.com/csob/paymentgateway/wiki/Payment-methods-and-processing-FAQ) includes answers related mainly to the business functionality of the gateway. More detail to previously solved problems can be also found in the GitHub Issues.
