package com.example.OnlineShop.mapper;

import com.example.OnlineShop.dto.CartItemResponse;
import com.example.OnlineShop.dto.CartResponse;
import com.example.OnlineShop.model.Cart;
import com.example.OnlineShop.model.CartItem;

import java.math.BigDecimal;
import java.util.List;

public class CartMapper {
    public static CartItemResponse mapToResponse(CartItem item){
        return CartItemResponse.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .price(item.getProduct().getPrice())
                .subtotal(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .build();
    }

    public static CartResponse mapToResponses(Cart cart, BigDecimal totalPrice){
       return CartResponse.builder()
                .items(cart.getItems()
                        .stream()
                        .map(item -> mapToResponse(item))
                .toList())
                .totalPrice(totalPrice)
                .build();
    }
}
