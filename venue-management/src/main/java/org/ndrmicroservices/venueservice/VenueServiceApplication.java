package org.ndrmicroservices.venueservice;

import lombok.extern.slf4j.Slf4j;
import org.ndrmicroservices.venueservice.dto.ConsumeObject;
import org.ndrmicroservices.venueservice.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class VenueServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VenueServiceApplication.class, args);
    }

    private final VenueService venueService;

    @Autowired
    public VenueServiceApplication(VenueService venueService) {
        this.venueService = venueService;
    }

    @KafkaListener(topics = "sensor-data")
    public void listen(ConsumeObject message) {
        log.info("Got message <{}>" + message);
        venueService.updateVenueOccupancy(message);
    }
}
