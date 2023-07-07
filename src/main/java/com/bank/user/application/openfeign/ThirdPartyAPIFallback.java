package com.bank.user.application.openfeign;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("thirdPartyAPIFallback")
public class ThirdPartyAPIFallback implements ErrorDecoder  {

 private static Logger log = LoggerFactory.getLogger(ThirdPartyAPIFallback.class);

    @SneakyThrows
    @Override
    public Exception decode(String s, Response response) {
        FeignException exception = feign.FeignException.errorStatus(s, response);
        if (response.status() >= 500) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    null,
                    response.request());
        }
        switch (response.status()) {
            case 400:
                return new InterruptedException();
            case 404:
                return new InterruptedException("Url not found");
//            case 503:
//                return new InterruptedException("API is unavailable");
//            case 500: {
//                String error = IOUtils.toString(response.body().asInputStream(), "UTF-8");
//                return new RuntimeException(error);
//            }
            default:
                return new Exception("Exception while getting details");
        }
    }
}
