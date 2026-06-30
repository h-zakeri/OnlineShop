package com.example.OnlineShop.service;

import com.example.OnlineShop.exception.ResourceNotFoundException;
import com.example.OnlineShop.model.Category;
import com.example.OnlineShop.model.Product;
import com.example.OnlineShop.model.User;
import com.example.OnlineShop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private static final Logger logger =
            LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public List<Product> getProductsByCategory(Category category){
        if(category != null){
            return productRepository.findByCategory(category);
        }else {
            return productRepository.findAll();
        }
    }

    public Product createProduct(Product product){
        logger.info("Creating product: {}", product.getName());

        Product savedProduct = productRepository.save(product);

        logger.info("Product created successfully. ID: {}", savedProduct.getId());

        return savedProduct;
    }

    public Product updateProduct(Long id,Product updatedProduct){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setCategory(updatedProduct.getCategory());
        product.setPrice(updatedProduct.getPrice());
        product.setStockQuantity(updatedProduct.getStockQuantity());
        product.setImageUrl(updatedProduct.getImageUrl());
        return productRepository.save(product);
    }

    public Product updateProductCategory(Long id,Category updatedCategory){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setCategory(updatedCategory);

        return productRepository.save(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }


    public List<Product> getProducts(Category category, String name) {

        boolean hasTitle = (name != null && !name.isBlank());
        if(category != null && hasTitle){
            return productRepository.findByCategoryAndNameContaining(category,name);
        }else if(category != null){
            return productRepository.findByCategory(category);
        }else if(hasTitle){
            return productRepository.findByNameContaining(name);
        }
        return productRepository.findAll();
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }
}