package com.example.OnlineShop.dto;

import com.example.OnlineShop.model.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private int stockQuantity;

    private String imageUrl;


    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Category category;
}
