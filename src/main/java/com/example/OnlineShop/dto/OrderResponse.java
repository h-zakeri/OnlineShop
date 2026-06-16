package com.example.OnlineShop.dto;

import com.example.OnlineShop.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private OrderStatus status;
    private List<OrderItemResponse> items;
    private BigDecimal totalPrice;
}
