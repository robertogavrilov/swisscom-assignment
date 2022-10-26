package com.swisscom.demo.productservice.model.response;

import com.swisscom.demo.productservice.model.Product;

public class ClientProduct {

    private Product product;

    private int availableQuantity;

    public ClientProduct(Product product, int availableQuantity) {
        this.product = product;
        this.availableQuantity = availableQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }
}
