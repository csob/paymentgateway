package cz.monetplus.mips.eapi.v19.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.monetplus.mips.eapi.v19.connector.entity.*;
import cz.monetplus.mips.eapi.v19.connector.entity.responses.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class ExamplesServiceImpl implements ExamplesService {

    public static final String UNABLE_TO_DESERIALIZE_INIT_FILE = "Unable to deserialize init file";
    @Value("${merchant.id}")
    private String merchantId;
    private final NativeApiV19Service nativeApiV19Service;

    public ExamplesServiceImpl(NativeApiV19Service nativeApiV19Service) {
        this.nativeApiV19Service = nativeApiV19Service;
    }

    @Override
    public @NonNull PaymentInitResponse paymentInit(@NonNull File initFile) throws MipsException {
        PaymentInitRequest req;
        try {
            Gson gson = new GsonBuilder().create();
            req = gson.fromJson(FileUtils.readFileToString(initFile, StandardCharsets.UTF_8),
                    PaymentInitRequest.class);
        } catch (Exception e) {
            log.error(null, e);
            throw new MipsException(RespCode.INTERNAL_ERROR, UNABLE_TO_DESERIALIZE_INIT_FILE);
        }
        req.setMerchantId(merchantId);
        return nativeApiV19Service.paymentInit(req);
    }

    @Override
    public @NonNull PaymentStatusResponse paymentStatus(@NonNull String payId) throws MipsException {
        PaymentStatusRequest request = new PaymentStatusRequest(merchantId, payId);
        return nativeApiV19Service.paymentStatus(request);
    }

    @Override
    public @NonNull EchoResponse echoGet() throws MipsException {
        return nativeApiV19Service.echoGet(new EchoRequest(merchantId));
    }

    @Override
    public @NonNull EchoResponse echoPost() throws MipsException {
        return nativeApiV19Service.echoPost(new EchoRequest(merchantId));
    }

    @Override
    public @NonNull URI paymentProcess(@NonNull String payId) throws MipsException {
        return nativeApiV19Service.paymentProcess(new PaymentProcessRequest(merchantId, payId));
    }

    @Override
    public @NonNull PaymentResponse paymentClose(@NonNull String payId) throws MipsException {
        return nativeApiV19Service.paymentClose(new PaymentCloseRequest(merchantId, payId));
    }

    @Override
    public @NonNull PaymentResponse paymentReverse(@NonNull String payId) throws MipsException {
        return nativeApiV19Service.paymentReverse(new PaymentReverseRequest(merchantId, payId));
    }

    @Override
    public @NonNull PaymentResponse paymentRefund(@NonNull String payId) throws MipsException {
        return nativeApiV19Service.paymentRefund(new PaymentRefundRequest(merchantId, payId));
    }

    @Override
    public @NonNull OneclickEchoResponse oneclickEcho(@NonNull String origPayId) throws MipsException {
        return nativeApiV19Service.oneclickEcho(new OneclickEchoRequest(merchantId, origPayId));
    }

    @Override
    public @NonNull OneclickInitResponse oneclickInit(@NonNull File initFile) throws MipsException {
        OneclickInitRequest request;
        try {
            Gson gson = new GsonBuilder().create();
            request = gson.fromJson(FileUtils.readFileToString(initFile, StandardCharsets.UTF_8),
                    OneclickInitRequest.class);
        } catch (Exception e) {
            log.error(null, e);
            throw new MipsException(RespCode.INTERNAL_ERROR, UNABLE_TO_DESERIALIZE_INIT_FILE);
        }
        request.setMerchantId(merchantId);
        return nativeApiV19Service.oneclickInit(request);
    }

    @Override
    public @NonNull OneclickProcessResponse oneclickProcess(@NonNull String payId) throws MipsException {
        OneclickProcessRequest oneclickProcessRequest = new OneclickProcessRequest(merchantId, payId);
        oneclickProcessRequest.setFingerprint(new AuthData());
        oneclickProcessRequest.getFingerprint().setBrowser(Auth3dsBrowser.getDefaultBrowserParams());
        return nativeApiV19Service.oneclickProcess(oneclickProcessRequest);
    }

    @Override
    public @NonNull ApplepayEchoResponse applepayEcho() throws MipsException {
        return nativeApiV19Service.applepayEcho(new ApplepayEchoRequest(merchantId));
    }

    @Override
    public @NonNull GooglepayEchoResponse googlepayEcho() throws MipsException {
        return nativeApiV19Service.googlepayEcho(new GooglepayEchoRequest(merchantId));
    }

    @Override
    public @NonNull ApplepayInitResponse applepayInit(@NonNull File initFile, String payload) throws MipsException {
        ApplepayInitRequest req;
        try {
            Gson gson = new GsonBuilder().create();
            req = gson.fromJson(FileUtils.readFileToString(initFile, StandardCharsets.UTF_8),
                    ApplepayInitRequest.class);
        } catch (Exception e) {
            log.error(null, e);
            throw new MipsException(RespCode.INTERNAL_ERROR, UNABLE_TO_DESERIALIZE_INIT_FILE);
        }
        req.setMerchantId(merchantId);
        if(null != payload) req.setPayload(payload);
        return nativeApiV19Service.applepayInit(req);
    }

    @Override
    public @NonNull GooglepayInitResponse googlepayInit(@NonNull File initFile, String payload) throws MipsException {
        GooglepayInitRequest req;
        try {
            Gson gson = new GsonBuilder().create();
            req = gson.fromJson(FileUtils.readFileToString(initFile, StandardCharsets.UTF_8),
                    GooglepayInitRequest.class);
        } catch (Exception e) {
            log.error(null, e);
            throw new MipsException(RespCode.INTERNAL_ERROR, UNABLE_TO_DESERIALIZE_INIT_FILE);
        }
        req.setMerchantId(merchantId);
        if(null != payload) req.setPayload(payload);
        return nativeApiV19Service.googlepayInit(req);
    }

    @Override
    public @NonNull ApplepayProcessResponse applepayProcess(@NonNull String payId) throws MipsException {
        return nativeApiV19Service.applepayProcess(new ApplepayProcessRequest(merchantId, payId));
    }

    @Override
    public @NonNull GooglepayProcessResponse googlepayProcess(@NonNull String payId) throws MipsException {
        return nativeApiV19Service.googlepayProcess(new GooglepayProcessRequest(merchantId, payId));
    }

    @Override
    public @NonNull ButtonInitResponse buttonInit(@NonNull File initFile) throws MipsException {
        ButtonInitRequest req;
        try {
            Gson gson = new GsonBuilder().create();
            req = gson.fromJson(FileUtils.readFileToString(initFile, StandardCharsets.UTF_8),
                    ButtonInitRequest.class);
        } catch (Exception e) {
            log.error(null, e);
            throw new MipsException(RespCode.INTERNAL_ERROR, UNABLE_TO_DESERIALIZE_INIT_FILE);
        }
        req.setMerchantId(merchantId);
        return nativeApiV19Service.buttonInit(req);
    }

    @Override
    public @NonNull MallpayInitResponse mallpayInit(@NonNull File initFile) throws MipsException {
        MallpayInitRequest req;
        try {
            Gson gson = new GsonBuilder().create();
            req = gson.fromJson(FileUtils.readFileToString(initFile, StandardCharsets.UTF_8),
                    MallpayInitRequest.class);
        } catch (Exception e) {
            log.error(null, e);
            throw new MipsException(RespCode.INTERNAL_ERROR, UNABLE_TO_DESERIALIZE_INIT_FILE);
        }
        req.setMerchantId(merchantId);
        return nativeApiV19Service.mallpayInit(req);
    }

    @Override
    public @NonNull PaymentResponse mallpayCancel(@NonNull String payId, String reason) throws MipsException {
        return nativeApiV19Service.mallpayCancel(new MallpayCancelRequest(merchantId, payId, reason));
    }

    @Override
    public @NonNull PaymentResponse mallpayLogistics(@NonNull File initFile, String payId) throws MipsException {
        MallpayLogisticsRequest req;
        try {
            Gson gson = new GsonBuilder().create();
            req = gson.fromJson(FileUtils.readFileToString(initFile, StandardCharsets.UTF_8),
                    MallpayLogisticsRequest.class);
        } catch (Exception e) {
            log.error(null, e);
            throw new MipsException(RespCode.INTERNAL_ERROR, UNABLE_TO_DESERIALIZE_INIT_FILE);
        }
        req.setMerchantId(merchantId);
        if(null != payId) req.setPayId(payId);
        return nativeApiV19Service.mallpayLogistics(req);
    }

    @Override
    public @NonNull PaymentResponse mallpayRefund(@NonNull File initFile, String payId) throws MipsException {
        MallpayRefundRequest req;
        try {
            Gson gson = new GsonBuilder().create();
            req = gson.fromJson(FileUtils.readFileToString(initFile, StandardCharsets.UTF_8),
                    MallpayRefundRequest.class);
        } catch (Exception e) {
            log.error(null, e);
            throw new MipsException(RespCode.INTERNAL_ERROR, UNABLE_TO_DESERIALIZE_INIT_FILE);
        }
        req.setMerchantId(merchantId);
        if(null != payId) req.setPayId(payId);
        return nativeApiV19Service.mallpayRefund(req);
    }

    @Override
    public @NonNull LoanInitResponse loanInit(@NonNull File initFile) throws MipsException {
        LoanInitRequest req;
        try {
            Gson gson = new GsonBuilder().create();
            req = gson.fromJson(FileUtils.readFileToString(initFile, StandardCharsets.UTF_8),
                    LoanInitRequest.class);
        } catch (Exception e) {
            log.error(null, e);
            throw new MipsException(RespCode.INTERNAL_ERROR, UNABLE_TO_DESERIALIZE_INIT_FILE);
        }
        req.setMerchantId(merchantId);
        return nativeApiV19Service.loanInit(req);
    }

    @Override
    public @NonNull PaymentResponse loanCancel(@NonNull String payId, String reason) throws MipsException {
        return nativeApiV19Service.loanCancel(new LoanCancelRequest(merchantId, payId, reason));
    }

    @Override
    public @NonNull PaymentResponse loanLogistics(@NonNull File initFile) throws MipsException {
        LoanLogisticsRequest req;
        try {
            Gson gson = new GsonBuilder().create();
            req = gson.fromJson(FileUtils.readFileToString(initFile, StandardCharsets.UTF_8),
                    LoanLogisticsRequest.class);
        } catch (Exception e) {
            log.error(null, e);
            throw new MipsException(RespCode.INTERNAL_ERROR, UNABLE_TO_DESERIALIZE_INIT_FILE);
        }
        req.setMerchantId(merchantId);
        return nativeApiV19Service.loanLogistics(req);
    }

    @Override
    public @NonNull PaymentResponse loanRefund(@NonNull File initFile) throws MipsException {
        LoanRefundRequest req;
        try {
            Gson gson = new GsonBuilder().create();
            req = gson.fromJson(FileUtils.readFileToString(initFile, StandardCharsets.UTF_8),
                    LoanRefundRequest.class);
        } catch (Exception e) {
            log.error(null, e);
            throw new MipsException(RespCode.INTERNAL_ERROR, UNABLE_TO_DESERIALIZE_INIT_FILE);
        }
        req.setMerchantId(merchantId);
        return nativeApiV19Service.loanRefund(req);
    }

    @Override
    public @NonNull EchoCustomerResponse echoCustomer(@NonNull String customerId) throws MipsException {
        return nativeApiV19Service.echoCustomer(new EchoCustomerRequest(merchantId, customerId));
    }
}
