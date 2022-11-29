package cz.monetplus.mips.eapi.v19.service;

import java.io.File;
import java.net.URI;

import cz.monetplus.mips.eapi.v19.connector.entity.responses.*;
import lombok.NonNull;

public interface ExamplesService {
    @NonNull EchoResponse echoPost() throws MipsException;
    @NonNull EchoResponse echoGet() throws MipsException;

    @NonNull PaymentInitResponse paymentInit(@NonNull File initFile) throws MipsException;
    @NonNull PaymentStatusResponse paymentStatus(@NonNull String payId) throws MipsException;
    @NonNull URI paymentProcess(@NonNull String payId) throws MipsException;

    @NonNull PaymentResponse paymentClose(@NonNull String payId) throws MipsException;
    @NonNull PaymentResponse paymentReverse(@NonNull String payId) throws MipsException;
    @NonNull PaymentResponse paymentRefund(@NonNull String payId) throws MipsException;

    @NonNull OneclickEchoResponse oneclickEcho(@NonNull String origPayId) throws MipsException;
    @NonNull OneclickInitResponse oneclickInit(@NonNull File initFile) throws MipsException;
    @NonNull OneclickProcessResponse oneclickProcess(@NonNull String payId) throws MipsException;

    @NonNull ApplepayEchoResponse applepayEcho() throws MipsException;
    @NonNull ApplepayInitResponse applepayInit(@NonNull File initFile, String payload) throws MipsException;
    @NonNull ApplepayProcessResponse applepayProcess(@NonNull String payId) throws MipsException;

    @NonNull GooglepayEchoResponse googlepayEcho() throws MipsException;
    @NonNull GooglepayInitResponse googlepayInit(@NonNull File initFile, String payload) throws MipsException;
    @NonNull GooglepayProcessResponse googlepayProcess(@NonNull String payId) throws MipsException;

    @NonNull ButtonInitResponse buttonInit(@NonNull File initFile) throws MipsException;

    @NonNull MallpayInitResponse mallpayInit(@NonNull File initFile) throws MipsException;
    @NonNull PaymentResponse mallpayCancel(@NonNull String payId, @NonNull String reason) throws MipsException;
    @NonNull PaymentResponse mallpayLogistics(@NonNull File initFile, String payId) throws MipsException;
    @NonNull PaymentResponse mallpayRefund(@NonNull File initFile, String payId) throws MipsException;

    @NonNull LoanInitResponse loanInit(@NonNull File initFile) throws MipsException;
    @NonNull PaymentResponse loanCancel(@NonNull String payId, @NonNull String reason) throws MipsException;
    @NonNull PaymentResponse loanLogistics(@NonNull File initFile) throws MipsException;
    @NonNull PaymentResponse loanRefund(@NonNull File initFile) throws MipsException;

    @NonNull EchoCustomerResponse echoCustomer(@NonNull String customerId) throws MipsException;
}
