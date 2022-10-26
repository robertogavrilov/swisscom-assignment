package com.swisscom.demo.productservice.model.requests;

public class IncreaseStockItemRequest {

    private Long productId;

    private int quantity;

    private IncreaseStockItemRequest() {
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
