package com.swisscom.demo.orderservice.exceptionhandler;

import com.swisscom.demo.orderservice.apierror.ApiError;
import com.swisscom.demo.orderservice.exceptions.BadRequestException;
import com.swisscom.demo.orderservice.exceptions.ServiceException;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

public class GlobalErrorHandler {

    public static Mono<ResponseStatusException> byStatus(ClientResponse clientResponse) {

        if (clientResponse.statusCode().is4xxClientError()) {
            return clientResponse.bodyToMono(ApiError.class)
                    .flatMap(errorBody -> Mono.error(new BadRequestException(errorBody.getMessage())));
        } else {
            return clientResponse.bodyToMono(String.class)
                    .flatMap(errorBody -> Mono.error(new ServiceException(errorBody)));
        }
    }
}
