package com.localsvcfinder.controller;

import com.localsvcfinder.model.User;
import com.localsvcfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userService.loginUser(email, password);
        return user.isPresent() ? "Login Successful" : "Invalid Credentials";
    }
}
