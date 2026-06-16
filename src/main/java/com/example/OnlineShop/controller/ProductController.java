package com.example.OnlineShop.controller;

import com.example.OnlineShop.dto.CreateProductRequest;
import com.example.OnlineShop.dto.ProductResponse;
import com.example.OnlineShop.dto.UpdateProductRequest;
import com.example.OnlineShop.mapper.ProductMapper;
import com.example.OnlineShop.model.Product;
import com.example.OnlineShop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;



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
    public List<ProductResponse> getProducts(){
        List<Product> products = productService.getAllProduct();
        List<ProductResponse> responses = new ArrayList<>();
        for(Product product : products){
            responses.add(ProductMapper.mapToResponse(product));
        }
        return responses;
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