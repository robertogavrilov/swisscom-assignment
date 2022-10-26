package com.swisscom.demo.productservice.model.requests;

import java.util.Map;

public class OrderRequest {

    private Map<Long, Integer> orderingProducts; // Long-productId, Integer-quantity

    private OrderRequest() {
    }

    public Map<Long, Integer> getOrderingProducts() {
        return orderingProducts;
    }
}