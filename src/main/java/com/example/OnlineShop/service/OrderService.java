package com.example.OnlineShop.service;

import com.example.OnlineShop.exception.BadRequestException;
import com.example.OnlineShop.exception.ResourceNotFoundException;
import com.example.OnlineShop.model.*;
import com.example.OnlineShop.repository.CartRepository;
import com.example.OnlineShop.repository.OrderRepository;
import com.example.OnlineShop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(CartRepository cartRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // checkout

    @Transactional
    public Order createOrder(User user) {
        Cart cart = cartRepository.findByOwner(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getStockQuantity() < item.getQuantity()) {
                throw new BadRequestException("There is no enough Quantity for the product " + item.getProduct().getName());
            }
        }
        Order order = new Order();
        order.setOwner(user);
        for (CartItem item : cart.getItems()) {
            OrderItem newItem = new OrderItem();
            newItem.setProduct(item.getProduct());
            newItem.setPrice(item.getProduct().getPrice());
            newItem.setQuantity(item.getQuantity());
            newItem.setOrder(order);
            newItem.getProduct().setStockQuantity(
            newItem.getProduct().getStockQuantity() - item.getQuantity()
            );
            productRepository.save(newItem.getProduct());
            order.getItems().add(newItem);
        }

        cart.getItems().clear();
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        cartRepository.save(cart);
        return order;
    }


    public List<Order> getOrders(User user) {
        return orderRepository.findByOwner(user);
    }

    public Order getOrderById(User user, Long orderId) {
        return orderRepository.findByIdAndOwner(orderId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public BigDecimal calculateTotal(Order order){
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            total = total.add(
                    item.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }
        return total;
    }

    public void cancelOrder(Long orderId,User user){
        Order order = orderRepository.findByIdAndOwner(orderId,user)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    if(order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELLED){
        throw new BadRequestException("Order cannot be cancel");
    }
        for(OrderItem item : order.getItems()){
            item.getProduct().setStockQuantity(
                    item.getProduct().getStockQuantity() + item.getQuantity()
            );
            productRepository.save(item.getProduct());
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public void completeOrder(Long orderId,User user){
        Order order = orderRepository.findByIdAndOwner(orderId,user)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        if(order.getStatus() == OrderStatus.PENDING){
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
        }else{
            throw new BadRequestException("Order cannot be complete");
        }
    }
}