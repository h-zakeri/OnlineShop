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
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

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

    /////////// CREATE
    public Product createProduct(Product product){

        return productRepository.save(product);
    }


    /////////////// UPDATE

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

   // public Page<Product> getProducts(Pageable pageable) {
   //     return productRepository.findAll(pageable);
   // }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

  //  public Page<Product> getProducts(Category category, String name,Pageable pageable) {

    //    boolean hasTitle = (name != null && !name.isBlank());
      //  if(category != null && hasTitle){
        //    return productRepository.findByCategoryAndNameContaining(category,name,pageable);
     //   }else if(category != null){
       //     return productRepository.findByCategory(category,pageable);
    //    }else if(hasTitle){
      //      return productRepository.findByNameContaining(name, pageable);
    //    }
      //  return productRepository.findAll(pageable);
   // }


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
