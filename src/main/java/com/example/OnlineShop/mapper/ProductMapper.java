package com.example.OnlineShop.mapper;

import com.example.OnlineShop.dto.CreateProductRequest;
import com.example.OnlineShop.dto.ProductResponse;
import com.example.OnlineShop.model.Product;

public class ProductMapper {
    public static ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .stockQuantity(product.getStockQuantity())
                .build();
    }

    public static Product mapToProduct(CreateProductRequest newProduct) {
        return Product.builder()
                .name(newProduct.getName())
                .description(newProduct.getDescription())
                .category(newProduct.getCategory())
                .price(newProduct.getPrice())
                .imageUrl(newProduct.getImageUrl())
                .stockQuantity(newProduct.getStockQuantity())
                .build();
    }
}