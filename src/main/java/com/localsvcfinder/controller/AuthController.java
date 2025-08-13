package com.localsvcfinder.controller;

import com.localsvcfinder.model.User;
import com.localsvcfinder.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ProviderService providerService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Optional<User> provider = providerService.loginProvider(email, password);

        if (provider.isPresent()) {
            // Return provider info (including id) as JSON
            return ResponseEntity.ok(provider.get());
        } else {
            // Return 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }
}
