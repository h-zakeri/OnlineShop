package com.example.OnlineShop.mapper;


import com.example.OnlineShop.dto.OrderItemResponse;
import com.example.OnlineShop.dto.OrderResponse;
import com.example.OnlineShop.model.Order;

import java.math.BigDecimal;
import java.util.List;

public class OrderMapper {

    public static OrderResponse mapToResponse(Order order,BigDecimal totalPrice) {

        List<OrderItemResponse> itemsResponse =
                order.getItems()
                .stream()
                .map(item -> OrderItemResponse.builder()
                        .productName(item.getProduct().getName())
                        .productId(item.getProduct().getId())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .build())
                .toList();
        return OrderResponse.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .items(itemsResponse)
                .totalPrice(totalPrice)
                .build();
    }
}