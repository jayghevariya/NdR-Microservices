package org.ndrmicroservices.venueservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class VenueServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VenueServiceApplication.class, args);
    }
}
