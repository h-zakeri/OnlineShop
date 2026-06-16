package com.example.OnlineShop.controller;

import com.example.OnlineShop.dto.OrderItemResponse;
import com.example.OnlineShop.dto.OrderResponse;
import com.example.OnlineShop.model.*;
import com.example.OnlineShop.service.OrderService;
import com.example.OnlineShop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.OnlineShop.mapper.OrderMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    public OrderController(OrderService orderService,UserService userService){
        this.orderService = orderService;
        this.userService = userService;
    }

    //create Order
    @PostMapping
    public OrderResponse createOrder(@RequestParam Long userId){
        User user = userService.getUserById(userId);
        Order order = orderService.createOrder(user);
        return OrderMapper.mapToResponse(order, orderService.calculateTotal(order));
    }

    //cancel order
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id,@RequestParam Long userId) {
        User user = userService.getUserById(userId);
        orderService.cancelOrder(id,user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Void> completeOrder(@PathVariable Long id,@RequestParam Long userId) {
        User user = userService.getUserById(userId);
        orderService.completeOrder(id,user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<OrderResponse> getOrders(@RequestParam Long userId){
        User user = userService.getUserById(userId);
        List<Order> orders = orderService.getOrders(user);
        return orders
                .stream()
                .map(order -> OrderMapper.mapToResponse(order,orderService.calculateTotal(order)))
                .toList();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id, @RequestParam Long userId){
        User user = userService.getUserById(userId);
        Order order = orderService.getOrderById(user,id);
        return OrderMapper.mapToResponse(order,orderService.calculateTotal(order));
    }
}