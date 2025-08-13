package com.localsvcfinder.controller;

import com.localsvcfinder.model.Service;
import com.localsvcfinder.model.User;
import com.localsvcfinder.repository.ServiceRepository;
import com.localsvcfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all services
    @GetMapping
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // Create a new service with provider
    @PostMapping
    public Service createService(@RequestBody Service service) {
        if (service.getProvider() != null && service.getProvider().getId() != null) {
            User provider = userRepository.findById(service.getProvider().getId())
                    .orElseThrow(() -> new RuntimeException("Provider not found"));
            service.setProvider(provider);
        }
        return serviceRepository.save(service);
    }

    // Get service by id
    @GetMapping("/{id}")
    public Service getServiceById(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }

    // Update service
    @PutMapping("/{id}")
    public Service updateService(@PathVariable Long id, @RequestBody Service updatedService) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setName(updatedService.getName());
        service.setCategory(updatedService.getCategory());
        service.setDescription(updatedService.getDescription());
        service.setPrice(updatedService.getPrice());
        service.setRating(updatedService.getRating());

        if (updatedService.getProvider() != null && updatedService.getProvider().getId() != null) {
            User provider = userRepository.findById(updatedService.getProvider().getId())
                    .orElseThrow(() -> new RuntimeException("Provider not found"));
            service.setProvider(provider);
        }

        return serviceRepository.save(service);
    }

    // Delete service
    @DeleteMapping("/{id}")
    public String deleteService(@PathVariable Long id) {
        serviceRepository.deleteById(id);
        return "Service deleted successfully!";
    }
    @PostMapping("/provider/{providerId}")
    public Service createServiceForProvider(@PathVariable Long providerId, @RequestBody Service service) {
        User provider = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        service.setProvider(provider);
        return serviceRepository.save(service);
    }
    @PostMapping("/provider/email/{providerEmail}")
    public Service createServiceForProviderEmail(@PathVariable String providerEmail, @RequestBody Service service) {
        User provider = userRepository.findByEmail(providerEmail)
                .orElseThrow(() -> new RuntimeException("Provider not found with email: " + providerEmail));
        service.setProvider(provider);
        return serviceRepository.save(service);
    }


    
}
