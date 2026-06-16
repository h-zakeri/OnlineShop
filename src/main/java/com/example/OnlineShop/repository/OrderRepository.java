package com.example.OnlineShop.repository;

import com.example.OnlineShop.model.Order;
import com.example.OnlineShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByOwner(User owner);
    Optional<Order> findByIdAndOwner(Long Id, User owner);
}
