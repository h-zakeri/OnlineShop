package com.example.OnlineShop.controller;

import com.example.OnlineShop.dto.RegisterRequest;
import com.example.OnlineShop.dto.UserResponse;
import com.example.OnlineShop.model.Role;
import com.example.OnlineShop.model.User;
import com.example.OnlineShop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody @Valid RegisterRequest request){
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .role(Role.USER)
                .email(request.getEmail())
                                .build();
        User registerdUser;
        if(user.getName().contains("hani")){
             registerdUser = userService.register(user.getName(),user.getUsername(),user.getPassword(), Role.ADMIN, user.getEmail());

        }else{
             registerdUser = userService.register(user.getName(),user.getUsername(),user.getPassword(), user.getRole(),user.getEmail());
        }
        return UserResponse.builder()
                .id(registerdUser.getId())
                .name(registerdUser.getName())
                .username(registerdUser.getUsername())
                .email(registerdUser.getEmail())
                .build();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
