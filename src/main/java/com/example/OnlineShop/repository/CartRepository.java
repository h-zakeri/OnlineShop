package com.example.OnlineShop.repository;

import com.example.OnlineShop.model.Cart;
import com.example.OnlineShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByOwner(User owner);
}