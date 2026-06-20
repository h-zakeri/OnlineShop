package com.example.OnlineShop.event;

import com.example.OnlineShop.model.Order;

public class OrderCompletedEvent {

    private final Order order;

    public OrderCompletedEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}