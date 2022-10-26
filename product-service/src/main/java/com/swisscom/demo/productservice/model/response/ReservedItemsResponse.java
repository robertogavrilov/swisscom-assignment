package com.swisscom.demo.productservice.model.response;

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
