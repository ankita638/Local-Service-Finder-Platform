package com.localsvcfinder.controller;

import com.localsvcfinder.model.Booking;
import com.localsvcfinder.model.User;
import com.localsvcfinder.service.BookingService;
import com.localsvcfinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    // Book a service
    @PostMapping("/book")
    public Booking bookService(@RequestParam Long serviceId,
                               @RequestParam String date,
                               @RequestParam String customerEmail) {
        return bookingService.bookService(serviceId, date, customerEmail);
    }

    // Get bookings for a customer
    @GetMapping("/customer/{customerId}")
    public List<Booking> getBookingsByCustomer(@PathVariable Long customerId) {
        User customer = userService.findById(customerId);
        return bookingService.getBookingsByCustomer(customer);
    }

    // Get bookings for a provider
    @GetMapping("/provider/{providerId}")
    public List<Booking> getBookingsByProvider(@PathVariable Long providerId) {
        User provider = userService.findById(providerId);
        return bookingService.getBookingsByProvider(provider);
    }

    // Update booking status
    @PutMapping("/{bookingId}/status")
    public Booking updateBookingStatus(@PathVariable Long bookingId, @RequestParam Booking.Status status) {
        return bookingService.updateBookingStatus(bookingId, status);
    }

    // ✅ Get all bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
    @GetMapping("/provider/{providerId}/by-category")
    public List<Booking> getBookingsByProviderAndCategory(
            @PathVariable Long providerId,
            @RequestParam String category) {
        User provider = userService.findById(providerId);

        if (provider.getRole() != User.Role.PROVIDER) {
            throw new IllegalArgumentException("User is not a provider: " + provider.getEmail());
        }

        return bookingService.getBookingsByProviderAndServiceCategory(provider, category);
    }
    @GetMapping("/provider/email")
    public List<Booking> getBookingsByProviderEmail(@RequestParam String email) {
        User provider = userService.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Provider not found with email: " + email));

        if (provider.getRole() != User.Role.PROVIDER) {
            throw new IllegalArgumentException("User is not a provider: " + email);
        }

        return bookingService.getBookingsByProvider(provider);
    }


}
