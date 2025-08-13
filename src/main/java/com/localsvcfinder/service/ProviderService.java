package com.localsvcfinder.service;

import com.localsvcfinder.model.Service; // 👈 This is YOUR model
import com.localsvcfinder.model.User;
import com.localsvcfinder.repository.ServiceRepository;
import com.localsvcfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
// 👇 Use fully qualified name for Spring's annotation
@org.springframework.stereotype.Service
public class ProviderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public User registerProvider(User provider) {
        provider.setRole(User.Role.PROVIDER);
        return userRepository.save(provider);
    }

    public java.util.Optional<User> loginProvider(String email, String password) {
        java.util.Optional<User> provider = userRepository.findByEmail(email);
        if (provider.isPresent() && provider.get().getRole() == User.Role.PROVIDER
                && provider.get().getPassword().equals(password)) {
            return provider;
        }
        return java.util.Optional.empty();
    }

    public Service addService(Service service, User provider) {
        service.setProvider(provider);
        return serviceRepository.save(service);
    }

    public java.util.List<Service> getServicesByProvider(User provider) {
        return serviceRepository.findByProvider(provider);
    }
}
