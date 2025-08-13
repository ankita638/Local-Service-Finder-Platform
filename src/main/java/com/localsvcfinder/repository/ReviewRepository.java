package com.localsvcfinder.repository;

import com.localsvcfinder.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // No providerId related methods needed now
}
