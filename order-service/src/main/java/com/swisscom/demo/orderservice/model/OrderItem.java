package com.swisscom.demo.orderservice.model;

import javax.persistence.*;

@Entity
@Table(name = "sw_order_item")
public class OrderItem {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OrderedProduct orderedProduct;

    @Version
    private Long version;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public OrderedProduct getOrderedProduct() {
        return orderedProduct;
    }

    public void setOrderedProduct(OrderedProduct orderedProduct) {
        this.orderedProduct = orderedProduct;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderedProduct=" + orderedProduct +
                ", version=" + version +
                '}';
    }
}
