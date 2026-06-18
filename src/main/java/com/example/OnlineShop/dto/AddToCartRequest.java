package com.example.OnlineShop.dto;

import com.example.OnlineShop.model.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddToCartRequest {
    @NotNull
    private Long productId;

    @Min(1)
    private int quantity;
}