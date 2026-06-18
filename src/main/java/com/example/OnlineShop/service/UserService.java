package com.example.OnlineShop.service;

import com.example.OnlineShop.exception.ResourceNotFoundException;
import com.example.OnlineShop.model.Cart;
import com.example.OnlineShop.model.Role;
import com.example.OnlineShop.model.User;
import com.example.OnlineShop.repository.CartRepository;
import com.example.OnlineShop.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,CartRepository cartRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
    }

    public User register(String name,String username, String password, Role role,String email) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setName(name);
        user.setRole(role);
        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setOwner(savedUser);
        cartRepository.save(cart);

        return savedUser;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->  new ResourceNotFoundException("User not found"));
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() ->  new ResourceNotFoundException("User not found"));
    }
}