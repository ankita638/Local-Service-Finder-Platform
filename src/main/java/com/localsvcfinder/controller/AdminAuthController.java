package com.localsvcfinder.controller;

import com.localsvcfinder.model.User;
import com.localsvcfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String adminLogin(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent()
                && user.get().getPassword().equals(password)  // For production, hash and verify properly
                && user.get().getRole() == User.Role.ADMIN) {
            return "Admin login successful";
        }
        return "Invalid admin credentials";
    }
}
