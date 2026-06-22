package com.example.OnlineShop.controller;

import com.example.OnlineShop.dto.CreateProductRequest;
import com.example.OnlineShop.dto.ProductResponse;
import com.example.OnlineShop.dto.UpdateProductRequest;
import com.example.OnlineShop.mapper.ProductMapper;
import com.example.OnlineShop.model.Category;
import com.example.OnlineShop.model.Product;
import com.example.OnlineShop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody CreateProductRequest request){
        Product product = ProductMapper.mapToProduct(request);

        return ProductMapper.mapToResponse(productService.createProduct(product)) ;
    }

    @GetMapping
    public Page<ProductResponse> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        return productService.getAllProduct(page, size,sortBy,category,minPrice,maxPrice)
                .map(ProductMapper::mapToResponse);

    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id){

        return ProductMapper.mapToResponse(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request){
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .stockQuantity(request.getStockQuantity())
                .build();
        
        Product updated = productService.updateProduct(id,product);
        return ResponseEntity.ok(ProductMapper.mapToResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}