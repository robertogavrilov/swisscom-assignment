package com.swisscom.demo.orderservice.service.impl;

import com.swisscom.demo.orderservice.exceptions.BadRequestException;
import com.swisscom.demo.orderservice.exceptions.RecordNotFoundException;
import com.swisscom.demo.orderservice.model.Order;
import com.swisscom.demo.orderservice.model.OrderItem;
import com.swisscom.demo.orderservice.model.OrderedProduct;
import com.swisscom.demo.orderservice.model.enums.OrderState;
import com.swisscom.demo.orderservice.model.requests.OrderRequest;
import com.swisscom.demo.orderservice.model.response.ReservedItemsResponse;
import com.swisscom.demo.orderservice.repository.OrderRepository;
import com.swisscom.demo.orderservice.service.CommunicationService;
import com.swisscom.demo.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final CommunicationService communicationService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public OrderServiceImpl(OrderRepository orderRepository, CommunicationService communicationService) {
        this.orderRepository = orderRepository;
        this.communicationService = communicationService;
    }

    @Override
    @Transactional
    public Order createOrder(OrderRequest orderRequest) {

        // Ordering items
        ReservedItemsResponse reservedItemsResponse = communicationService.orderProducts(orderRequest);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderedProduct orderedProduct : reservedItemsResponse.getReservedItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderedProduct(orderedProduct);
            orderItems.add(orderItem);
        }

        Order order = new Order(orderItems);

        order = orderRepository.save(order);

        logger.info("Successfully created order [{}].", order);

        return order;
    }

    @Override
    public Order changeOrderState(Long orderId, OrderState orderState) {
        logger.info("Attempt to change state of the order [{}] to [{}].", orderId, orderState);

        Order order = getOrder(orderId);

        if (OrderState.RUNNING != order.getOrderState()) {
            logger.error("Can not change state if order is in [{}] state.", order.getOrderState());
            throw new BadRequestException(String.format("Can not change state if order is in %s state", order.getOrderState()));
        }

        updateStock(order, orderState);

        order.setOrderState(orderState);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order does not exist"));
    }

    @Override
    public List<Order> filterOrders(Long id) {
        if (id == null) {
            return orderRepository.findAll();
        }
        return Collections.singletonList(getOrder(id));
    }

    private void updateStock(Order order, OrderState orderState) {
        List<Long> reservedStockIds = order.getOrderItems().stream()
                .map(o -> o.getOrderedProduct().getReservedStockItemId())
                .collect(Collectors.toList());
        communicationService.stockUpdate(reservedStockIds, orderState);
    }
}
