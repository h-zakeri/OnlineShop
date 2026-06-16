package com.example.OnlineShop.service;

import com.example.OnlineShop.exception.BadRequestException;
import com.example.OnlineShop.model.Cart;
import com.example.OnlineShop.model.CartItem;
import com.example.OnlineShop.model.Product;
import com.example.OnlineShop.model.User;
import com.example.OnlineShop.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartItem addToCart(User user, Product product, int quantity) {

        if(quantity <= 0){
            return null;
        }
        Cart cart = findUserCart(user);

        CartItem existingItem = findItem(cart,product);

        if (existingItem != null) {
            if (existingItem.getProduct().getStockQuantity() >= existingItem.getQuantity() + quantity) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                cartRepository.save(cart);
                return existingItem;
            } else {
                throw new BadRequestException("Not enough stock");
            }
        } else {
            CartItem newItem = new CartItem(product, quantity, cart);
            if (product.getStockQuantity() >= quantity) {
                cart.getItems().add(newItem);
                cartRepository.save(cart);
                return newItem;
            }
        }
        return null;
    }

    public void removeItem(User user,Product product){
        Cart cart = findUserCart(user);

        CartItem existingItem = findItem(cart,product);
        if (existingItem != null) {
            cart.getItems().remove(existingItem);
            cartRepository.save(cart);
        }else{
            throw new RuntimeException("Item not found");
        }
    }

    public CartItem updateQuantity(User user,Product product, int newQuantity){
        if (newQuantity == 0){
            removeItem(user, product);
            return null;
        }
        Cart cart = findUserCart(user);

        CartItem existingItem = findItem(cart,product);
        if (existingItem != null) {
            if (existingItem.getProduct().getStockQuantity() >= (newQuantity-existingItem.getQuantity())) {
                existingItem.setQuantity(newQuantity);
                cartRepository.save(cart);
            }else{
                throw new BadRequestException("Not enough stock");
            }
        }
        return existingItem;
    }


    public BigDecimal calculateTotal(User user){
        Cart cart = findUserCart(user);

        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
           total = total.add((item.getProduct().getPrice()).multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }

    public void clearCart(User user){
        Cart cart = findUserCart(user);

        cart.getItems().clear();
        cartRepository.save(cart);
    }

    public Cart getCart(User user){
        return findUserCart(user);
    }

    private Cart findUserCart(User user){
       return cartRepository.findByOwner(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setOwner(user);
                    return cartRepository.save(newCart);
                });
    }

    private CartItem findItem(Cart cart, Product product){
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(product.getId())) {
                return item;
            }
        }
        return null;
    }
}
