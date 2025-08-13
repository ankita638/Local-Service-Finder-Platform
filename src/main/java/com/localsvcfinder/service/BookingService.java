package com.localsvcfinder.service;

import com.localsvcfinder.model.Booking;
import com.localsvcfinder.model.Service;  // Your entity
import com.localsvcfinder.model.User;
import com.localsvcfinder.repository.BookingRepository;
import com.localsvcfinder.repository.ServiceRepository;
import com.localsvcfinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

// Use fully qualified annotation to avoid collision with your entity class
@org.springframework.stereotype.Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // Updated method to accept customerEmail explicitly and check customer role
    public Booking bookService(Long serviceId, String date, String customerEmail) {
        User customer = userRepository.findByEmail(customerEmail)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + customerEmail));

        // Check if user role is CUSTOMER
        if (customer.getRole() != User.Role.CUSTOMER) {
            throw new IllegalArgumentException("User is not a customer: " + customerEmail);
        }

        Service service = serviceRepository.findById(serviceId)
            .orElseThrow(() -> new IllegalArgumentException("Service with ID " + serviceId + " not found"));

        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setProvider(service.getProvider());
        booking.setService(service);
        booking.setDate(date);
        booking.setStatus(Booking.Status.PENDING);

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByCustomer(User customer) {
        return bookingRepository.findByCustomer(customer);
    }

    public List<Booking> getBookingsByProvider(User provider) {
        return bookingRepository.findByProvider(provider);
    }

    // ✅ Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking updateBookingStatus(Long bookingId, Booking.Status status) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking with ID " + bookingId + " not found"));

        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
    public List<Booking> getBookingsByProviderAndServiceCategory(User provider, String category) {
        return bookingRepository.findByProviderAndService_Category(provider, category);
    }
    public List<Booking> getBookingsByProviderEmail(String email) {
        User provider = userRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Provider not found with email: " + email));
        if (provider.getRole() != User.Role.PROVIDER) {
            throw new IllegalArgumentException("User is not a provider: " + email);
        }
        return bookingRepository.findByProvider(provider);
    }


}
