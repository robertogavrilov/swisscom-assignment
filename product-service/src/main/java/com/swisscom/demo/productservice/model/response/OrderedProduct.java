package com.swisscom.demo.productservice.model.response;


public class OrderedProduct {

    private Long productId;

    private Long reservedStockItemId;

    private String name;

    private String type;

    private String description;

    private OrderedProduct() {
    }

    public OrderedProduct(Long reservedStockItemId, Long productId, String name, String type, String description) {
        this.reservedStockItemId = reservedStockItemId;
        this.productId = productId;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getReservedStockItemId() {
        return reservedStockItemId;
    }

    public void setReservedStockItemId(Long reservedStockItemId) {
        this.reservedStockItemId = reservedStockItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + productId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}