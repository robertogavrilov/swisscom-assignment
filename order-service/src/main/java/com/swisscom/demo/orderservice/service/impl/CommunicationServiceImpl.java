package com.swisscom.demo.orderservice.service.impl;

import com.swisscom.demo.orderservice.exceptionhandler.GlobalErrorHandler;
import com.swisscom.demo.orderservice.model.enums.OrderState;
import com.swisscom.demo.orderservice.model.requests.OrderRequest;
import com.swisscom.demo.orderservice.model.response.ReservedItemsResponse;
import com.swisscom.demo.orderservice.service.CommunicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    private final WebClient.Builder webClientBuilder;

    @Value("${services.url.product-service}")
    private String productServiceUrl;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public CommunicationServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public ReservedItemsResponse orderProducts(OrderRequest orderRequest) {
        logger.info("Ordering products [{}}.", orderRequest);
        return webClientBuilder.build()
                .put()
                .uri(productServiceUrl + "/catalog/order")
                .body(BodyInserters.fromValue(orderRequest))
                .retrieve()
                .onStatus(HttpStatus::isError, GlobalErrorHandler::byStatus)
                .bodyToMono(ReservedItemsResponse.class)
                .block();
    }

    @Override
    public void stockUpdate(List<Long> reservedStockIds, OrderState orderState) {
        logger.info("Updating stock items [{}] triggered by [{}] status.", reservedStockIds, orderState);

        String stringifyReservedStockIds = reservedStockIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        webClientBuilder.build()
                .put()
                .uri(productServiceUrl + String.format("catalog/update-stock?reservedStockIds=%s&orderState=%s",
                        stringifyReservedStockIds, orderState))
                .retrieve()
                .onStatus(HttpStatus::isError, GlobalErrorHandler::byStatus)
                .bodyToMono(Void.class)
                .block();
    }
}
