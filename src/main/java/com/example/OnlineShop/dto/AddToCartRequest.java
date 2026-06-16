package com.example.OnlineShop.dto;

import com.example.OnlineShop.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddToCartRequest {
    private Long productId;
    private Long userId;
    private int quantity;
}
