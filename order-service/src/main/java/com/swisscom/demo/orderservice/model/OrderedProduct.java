package com.swisscom.demo.orderservice.model;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderedProduct {

    @Column
    private Long reservedStockItemId;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private String description;

    private OrderedProduct() {
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
        return "OrderedProduct{" +
                "reservedStockItemId=" + reservedStockItemId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}