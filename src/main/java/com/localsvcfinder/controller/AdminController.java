package com.localsvcfinder.controller;

import com.localsvcfinder.model.Booking;
import com.localsvcfinder.model.Service;
import com.localsvcfinder.model.User;
import com.localsvcfinder.repository.BookingRepository;
import com.localsvcfinder.repository.ServiceRepository;
import com.localsvcfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // ---------------- USERS ----------------
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        bookingRepository.deleteAll(bookingRepository.findByCustomer(user));
        bookingRepository.deleteAll(bookingRepository.findByProvider(user));
        serviceRepository.deleteAll(serviceRepository.findByProvider(user));
        userRepository.delete(user);

        return "User and related data deleted successfully!";
    }

    // ---------------- BOOKINGS ----------------
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @DeleteMapping("/bookings/{id}")
    public String deleteBooking(@PathVariable Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        bookingRepository.delete(booking);
        return "Booking deleted successfully!";
    }

    // ---------------- SERVICES ----------------
    @GetMapping("/services")
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @PostMapping("/services")
    public Service createService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    @PutMapping("/services/{id}")
    public Service updateService(@PathVariable Long id, @RequestBody Service updatedService) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        service.setName(updatedService.getName());
        service.setCategory(updatedService.getCategory());
        service.setDescription(updatedService.getDescription());
        service.setPrice(updatedService.getPrice());
        service.setRating(updatedService.getRating());
        service.setProvider(updatedService.getProvider());

        return serviceRepository.save(service);
    }

    @PostMapping("/bookings")
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingRepository.save(booking);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        System.out.println(">>>> Inside getUserById with id: " + id);
        return userRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @DeleteMapping("/services/{id}")
    public String deleteService(@PathVariable Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        serviceRepository.delete(service);
        return "Service deleted successfully!";
    }
}
