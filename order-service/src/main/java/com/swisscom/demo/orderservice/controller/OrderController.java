package com.swisscom.demo.orderservice.controller;

import com.swisscom.demo.orderservice.model.Order;
import com.swisscom.demo.orderservice.model.enums.OrderState;
import com.swisscom.demo.orderservice.model.requests.OrderRequest;
import com.swisscom.demo.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/products")
    public ResponseEntity<Order> orderProducts(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @GetMapping("/show-info")
    public ResponseEntity<List<Order>> showOrderInformation(@RequestParam(required = false) Long orderId) {
        return ResponseEntity.ok(orderService.filterOrders(orderId));
    }

    @PutMapping("/change-state")
    public ResponseEntity<Order> changeOrderState(@RequestParam Long orderId,
                                                  @RequestParam OrderState orderState) {
        return ResponseEntity.ok(orderService.changeOrderState(orderId, orderState));
    }
}
