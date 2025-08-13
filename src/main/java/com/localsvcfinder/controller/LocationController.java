package com.localsvcfinder.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")  // Base path
public class LocationController {

    @GetMapping("/providers")
    public List<Map<String, Object>> getNearbyProviders() {
        List<Map<String, Object>> providers = new ArrayList<>();

        providers.add(createProvider("Electrician", 22.5726, 88.3639));
        providers.add(createProvider("Plumber", 22.5740, 88.3645));
        providers.add(createProvider("Mechanic", 22.5700, 88.3620));
        providers.add(createProvider("Carpenter", 22.5730, 88.3650));

        return providers;
    }

    private Map<String, Object> createProvider(String type, double lat, double lon) {
        Map<String, Object> provider = new HashMap<>();
        provider.put("type", type);
        provider.put("lat", lat);
        provider.put("lon", lon);
        return provider;
    }
}
