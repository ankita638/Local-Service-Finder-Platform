package com.localsvcfinder.controller;

import com.localsvcfinder.model.Service;
import com.localsvcfinder.model.User;
import com.localsvcfinder.service.ProviderService;
import com.localsvcfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User provider) {
        return providerService.registerProvider(provider);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        Optional<User> provider = providerService.loginProvider(email, password);
        return provider.isPresent() ? "Login Successful" : "Invalid Credentials";
    }

    @PostMapping("/{providerId}/services")
    public Service addService(@PathVariable Long providerId, @RequestBody Service service) {
        User provider = userService.findById(providerId);
        return providerService.addService(service, provider);
    }

    @GetMapping("/{providerId}/services")
    public List<Service> getServices(@PathVariable Long providerId) {
        User provider = userService.findById(providerId);
        return providerService.getServicesByProvider(provider);
    }
  
}
