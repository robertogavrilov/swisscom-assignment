package com.swisscom.demo.productservice.service;

import com.swisscom.demo.productservice.model.enums.OrderState;
import com.swisscom.demo.productservice.model.requests.OrderRequest;
import com.swisscom.demo.productservice.model.response.OrderedProduct;

import java.util.List;

public interface ReservedStockService {

    void stockUpdate(List<Long> reservedStockIds, OrderState orderState);

    List<OrderedProduct> makeOrder(OrderRequest orderRequest);
}