package com.example.OnlineShop.service;

import com.example.OnlineShop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.OnlineShop.exception.ResourceNotFoundException;
import com.example.OnlineShop.model.Product;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.verify;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProductById(1L)
        );
    }

    @Test
    void shouldReturnProductWhenIdExists() {

        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(1000))
                .build();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        Assertions.assertEquals("Laptop", result.getName());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldReturnProducts() {
        List<Product> products = new ArrayList<>();
        int size = 3;
        for(int i =0;i<size; i++){
            Product product = Product.builder()
                    .id((long) i)
                    .name("Laptop" + i)
                    .price(BigDecimal.valueOf(i*1000))
                    .build();
            products.add(product);
        }

        when(productRepository.findAll())
                .thenReturn(products);

        List<Product> result = productService.getAllProduct();

        Assertions.assertEquals(size, result.size());

        verify(productRepository).findAll();
    }

    @Test
    void shouldCreateProduct() {
        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(1000))
                .build();

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        Product result = productService.createProduct(product);

        Assertions.assertEquals(product, result);

        verify(productRepository).save(product);
    }

    @Test
    void shouldUpdateProduct(){
        Product existingProduct = Product.builder()
                .id(1L)
                .name("Old Laptop")
                .price(BigDecimal.valueOf(1000))
                .build();

        Product updatedProduct = Product.builder()
                .name("New Laptop")
                .price(BigDecimal.valueOf(1500))
                .build();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(existingProduct));

        when(productRepository.save(existingProduct))
                .thenReturn(existingProduct);

        Product result = productService.updateProduct(1L, updatedProduct);

        Assertions.assertEquals("New Laptop", result.getName());
        Assertions.assertEquals(BigDecimal.valueOf(1500), result.getPrice());

        verify(productRepository).findById(1L);
        verify(productRepository).save(existingProduct);
    }

    @Test
    void shouldDeleteProduct(){
        Product product = Product.builder()
                .id(1L)
                .name("Laptop")
                .price(BigDecimal.valueOf(1000))
                .build();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).delete(product);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingProduct() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                Assertions.assertThrows(
                        ResourceNotFoundException.class,
                        () -> productService.updateProduct(1L, new Product())
                );

        Assertions.assertEquals(
                "Product not found",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception =
                Assertions.assertThrows(
                        ResourceNotFoundException.class,
                        () -> productService.deleteProduct(1L)
                );

        Assertions.assertEquals(
                "Product not found",
                exception.getMessage()
        );
    }
}