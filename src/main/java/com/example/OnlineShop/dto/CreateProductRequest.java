package com.example.OnlineShop.dto;

import com.example.OnlineShop.model.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @DecimalMin("0.01")
    private BigDecimal price;

    @Min(0)
    private int stockQuantity;

    private String imageUrl;

    @NotNull
    private Category category;
}
