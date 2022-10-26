package com.swisscom.demo.orderservice.service;

import com.swisscom.demo.orderservice.model.Order;
import com.swisscom.demo.orderservice.model.enums.OrderState;
import com.swisscom.demo.orderservice.model.requests.OrderRequest;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderRequest orderRequest);

    Order changeOrderState(Long orderId, OrderState orderState);

    Order getOrder(Long id);

    List<Order> filterOrders(Long id);
}
