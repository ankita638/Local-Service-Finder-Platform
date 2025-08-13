package com.localsvcfinder.repository;

import com.localsvcfinder.model.Service;
import com.localsvcfinder.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findByProvider(User provider);
}
