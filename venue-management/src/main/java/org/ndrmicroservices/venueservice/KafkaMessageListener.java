package org.ndrmicroservices.venueservice;


import org.ndrmicroservices.venueservice.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    private final VenueService venueService;

    @Autowired
    public KafkaMessageListener(VenueService venueService) {
        this.venueService = venueService;
    }

    @KafkaListener(topics = "sensor-data-topic", groupId = "venue-service-group")
    public void listen(String message) {
        venueService.updateVenueOccupancy(message);
    }
}
