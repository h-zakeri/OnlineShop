package com.example.OnlineShop.controller;

import com.example.OnlineShop.dto.AddToCartRequest;
import com.example.OnlineShop.dto.CartItemResponse;
import com.example.OnlineShop.dto.CartResponse;
import com.example.OnlineShop.model.Cart;
import com.example.OnlineShop.mapper.CartMapper;
import com.example.OnlineShop.model.Product;
import com.example.OnlineShop.model.User;
import com.example.OnlineShop.service.CartService;
import com.example.OnlineShop.service.ProductService;
import com.example.OnlineShop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    public CartController(CartService cartService,ProductService productService,UserService userService){
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping
    public CartItemResponse addToCart(@Valid @RequestBody AddToCartRequest request, Authentication authentication){

        Product requestedProduct = productService.getProductById(request.getProductId());

        User user = userService.findByUsername(authentication.getName());

        return CartMapper.mapToResponse(cartService.addToCart(user, requestedProduct,request.getQuantity()));
    }

    @PutMapping()
    public ResponseEntity<CartItemResponse> updateQuantity(@Valid @RequestBody AddToCartRequest request,Authentication authentication){
        Product requestedProduct = productService.getProductById(request.getProductId());
        User user = userService.findByUsername(authentication.getName());
        return ResponseEntity.ok(CartMapper.mapToResponse(cartService.updateQuantity(user, requestedProduct, request.getQuantity())));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long productId,Authentication authentication){
        Product requestedProduct = productService.getProductById(productId);
        User user = userService.findByUsername(authentication.getName());
        cartService.removeItem(user,requestedProduct);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(Authentication authentication){
        User user = userService.findByUsername(authentication.getName());
        cartService.clearCart(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public CartResponse getCart(Authentication authentication){
        User user = userService.findByUsername(authentication.getName());
        Cart cart = cartService.getCart(user);
        return CartMapper.mapToResponses(cart,cartService.calculateTotal(user));
    }
}