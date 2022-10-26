package com.swisscom.demo.orderservice.model.requests;

import java.util.Map;

public class OrderRequest {

    private Map<Long, Integer> orderingProducts;

    private OrderRequest() {
    }

    public Map<Long, Integer> getOrderingProducts() {
        return orderingProducts;
    }
}
