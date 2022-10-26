package com.swisscom.demo.orderservice.model.response;

import com.swisscom.demo.orderservice.model.OrderedProduct;

import java.util.List;

public class ReservedItemsResponse {

    private List<OrderedProduct> reservedItems;

    private ReservedItemsResponse() {
    }

    public ReservedItemsResponse(List<OrderedProduct> reservedItems) {
        this.reservedItems = reservedItems;
    }

    public List<OrderedProduct> getReservedItems() {
        return reservedItems;
    }
}
