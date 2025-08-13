package com.localsvcfinder.repository;

import com.localsvcfinder.model.Booking;
import com.localsvcfinder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer(User customer);
    List<Booking> findByProvider(User provider);

    // Add this method to find bookings by provider and service category
    List<Booking> findByProviderAndService_Category(User provider, String category);
}
