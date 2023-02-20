package cz.monetplus.mips.eapi.v19.service;

import javax.ws.rs.core.Response;

import cz.monetplus.mips.eapi.v19.connector.entity.*;
import cz.monetplus.mips.eapi.v19.connector.entity.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.monetplus.mips.eapi.v19.connector.INativeAPIv19Resource;
import lombok.NonNull;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class NativeApiV19ServiceImpl implements NativeApiV19Service {

    public static final String NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE = "No response from nativeAPI, http response ";
    private static final String NATIVE_API_CALL_FAILED = "nativeAPI call failed: ";
    private final INativeAPIv19Resource resource;

    @Autowired
    public NativeApiV19ServiceImpl(INativeAPIv19Resource nativeApiResource) {
        this.resource = nativeApiResource;
    }

    @Override
    @SignedApiMethod
    public @NonNull PaymentInitResponse paymentInit(@NonNull PaymentInitRequest request) throws MipsException {
        try (Response response = resource.paymentInit(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(PaymentInitResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull URI paymentProcess(@NonNull PaymentProcessRequest request) throws MipsException {
        try (Response response = resource.paymentProcess(request.getMerchantId(), request.getPayId(), request.getDttm(), URLEncoder.encode(request.getSignature(), StandardCharsets.UTF_8.toString()))) {
            if (response == null || response.getStatus() != 303) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.getLocation();
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull PaymentStatusResponse paymentStatus(@NonNull PaymentStatusRequest request) throws MipsException {
        try (Response response = resource.paymentStatus(request.getMerchantId(),
                request.getPayId(), request.getDttm(), URLEncoder.encode(request.getSignature(), StandardCharsets.UTF_8.toString()))) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(PaymentStatusResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }


    @Override
    @SignedApiMethod
    public @NonNull PaymentResponse paymentClose(@NonNull PaymentCloseRequest request) throws MipsException {
        try (Response response = resource.paymentClose(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR, NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(PaymentResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR, NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull PaymentResponse paymentReverse(@NonNull PaymentReverseRequest request) throws MipsException {
        try (Response response = resource.paymentReverse(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(PaymentResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull PaymentResponse paymentRefund(@NonNull PaymentRefundRequest request) throws MipsException {
        try {
            try (Response response = resource.paymentRefund(request)) {
                if (response == null || response.getStatus() != 200) {
                    throw new MipsException(RespCode.INTERNAL_ERROR,
                            NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                    + (response != null ? response.getStatus() : "--"));
                }
                return response.readEntity(PaymentResponse.class);
            }
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull EchoResponse echoGet(@NonNull EchoRequest request) throws MipsException {
        try (Response response = resource.echo(request.getMerchantId(), request.getDttm(), URLEncoder.encode(request.getSignature(), StandardCharsets.UTF_8.toString()))) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(EchoResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull EchoResponse echoPost(@NonNull EchoRequest request) throws MipsException {
        try (Response response = resource.echo(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        "No response from nativeAPI  http response "
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(EchoResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull OneclickEchoResponse oneclickEcho(@NonNull OneclickEchoRequest request) throws MipsException {
        try (Response response = resource.oneclickEcho(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(OneclickEchoResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull OneclickInitResponse oneclickInit(@NonNull OneclickInitRequest request) throws MipsException {
        try (Response response = resource.oneclickInit(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(OneclickInitResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull OneclickProcessResponse oneclickProcess(@NonNull OneclickProcessRequest request) throws MipsException {
        try(Response response = resource.oneclickProcess(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(OneclickProcessResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull ApplepayEchoResponse applepayEcho(@NonNull ApplepayEchoRequest request) throws MipsException {
        try (Response response = resource.applepayEcho(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(ApplepayEchoResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull GooglepayEchoResponse googlepayEcho(@NonNull GooglepayEchoRequest request) throws MipsException {
        try (Response response = resource.googlepayEcho(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(GooglepayEchoResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull ApplepayInitResponse applepayInit(@NonNull ApplepayInitRequest request) throws MipsException {
        try (Response response = resource.applepayInit(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(ApplepayInitResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull ApplepayProcessResponse applepayProcess(@NonNull ApplepayProcessRequest request) throws MipsException {
        try (Response response = resource.applepayProcess(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(ApplepayProcessResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull GooglepayInitResponse googlepayInit(@NonNull GooglepayInitRequest request) throws MipsException {
        try (Response response = resource.googlepayInit(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(GooglepayInitResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull GooglepayProcessResponse googlepayProcess(@NonNull GooglepayProcessRequest request) throws MipsException {
        try (Response response = resource.googlepayProcess(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(GooglepayProcessResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull ButtonInitResponse buttonInit(@NonNull ButtonInitRequest request) throws MipsException {
        try (Response response = resource.buttonInit(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(ButtonInitResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull MallpayInitResponse mallpayInit(@NonNull MallpayInitRequest request) throws MipsException {
        try (Response response = resource.mallpayInit(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(MallpayInitResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull PaymentResponse mallpayCancel(@NonNull MallpayCancelRequest request) throws MipsException {
        try (Response response = resource.mallpayCancel(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(PaymentResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull PaymentResponse mallpayLogistics(@NonNull MallpayLogisticsRequest request) throws MipsException {
        try (Response response = resource.mallpayLogistics(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(PaymentResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull PaymentResponse mallpayRefund(@NonNull MallpayRefundRequest request) throws MipsException {
        try (Response response = resource.mallpayRefund(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(PaymentResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull LoanInitResponse loanInit(@NonNull LoanInitRequest request) throws MipsException {
        try ( Response response = resource.loanInit(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(LoanInitResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull PaymentResponse loanCancel(@NonNull LoanCancelRequest request) throws MipsException {
        try (Response response = resource.loanCancel(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(PaymentResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull PaymentResponse loanLogistics(@NonNull LoanLogisticsRequest request) throws MipsException {
        try (Response response = resource.loanLogistics(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(PaymentResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull PaymentResponse loanRefund(@NonNull LoanRefundRequest request) throws MipsException {
        try (Response response = resource.loanRefund(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(PaymentResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }

    @Override
    @SignedApiMethod
    public @NonNull EchoCustomerResponse echoCustomer(@NonNull EchoCustomerRequest request) throws MipsException {
        try (Response response = resource.echoCustomer(request)) {
            if (response == null || response.getStatus() != 200) {
                throw new MipsException(RespCode.INTERNAL_ERROR,
                        NO_RESPONSE_FROM_NATIVE_API_HTTP_RESPONSE
                                + (response != null ? response.getStatus() : "--"));
            }
            return response.readEntity(EchoCustomerResponse.class);
        } catch (Exception e) {
            throw new MipsException(RespCode.INTERNAL_ERROR,
                    NATIVE_API_CALL_FAILED + e.getMessage());
        }
    }
}
