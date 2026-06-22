package com.example.OnlineShop.repository;

import com.example.OnlineShop.model.Category;
import com.example.OnlineShop.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    List<Product> findByCategory(Category category);

    //Page<Product> findByNameContaining(String name, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    List<Product> findByNameContaining(String name);

    List<Product> findByCategoryAndNameContaining(Category category, String name);
}
