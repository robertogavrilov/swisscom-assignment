package com.swisscom.demo.orderservice.model;

import com.swisscom.demo.orderservice.model.enums.OrderState;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sw_order")
public class Order {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderState orderState;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    @Version
    private Long version;

    private Order() {
    }

    public Order(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        this.orderState = OrderState.RUNNING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderState=" + orderState +
                ", orderItems=" + orderItems +
                ", version=" + version +
                '}';
    }
}
