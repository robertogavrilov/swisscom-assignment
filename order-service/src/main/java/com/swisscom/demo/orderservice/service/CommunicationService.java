package com.swisscom.demo.orderservice.service;

import com.swisscom.demo.orderservice.model.enums.OrderState;
import com.swisscom.demo.orderservice.model.requests.OrderRequest;
import com.swisscom.demo.orderservice.model.response.ReservedItemsResponse;

import java.util.List;

public interface CommunicationService {

    ReservedItemsResponse orderProducts(OrderRequest orderRequest);

    void stockUpdate(List<Long> productId, OrderState orderState);
}
