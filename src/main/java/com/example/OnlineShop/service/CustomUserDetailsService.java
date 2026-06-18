package com.example.OnlineShop.service;
import com.example.OnlineShop.exception.ResourceNotFoundException;
import com.example.OnlineShop.model.User;
import com.example.OnlineShop.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
   // 1. از UserRepository کاربر را با username پیدا کن.
   //         2. اگر نبود Exception بنداز.
   //         3. اگر بود، به فرمتی که Spring می‌خواهد تبدیلش کن.
    // 4. برگردان.
   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       System.out.println("Loading user: " + username);
      User user=  userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
       Collection<GrantedAuthority> authorities = new ArrayList<>();
       authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
       org.springframework.security.core.userdetails.User user1 = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
        return user1;
    }
}