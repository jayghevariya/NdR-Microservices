package org.ndrmicroservices.venueservice.controller;

import org.ndrmicroservices.venueservice.dto.VenueAvailabilityDto;
import org.ndrmicroservices.venueservice.model.Venue;
import org.ndrmicroservices.venueservice.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping("/{venueId}/availability")
    public VenueAvailabilityDto getVenueAvailability(@PathVariable Long venueId) {
        return venueService.getVenueAvailability(venueId);
    }

    @GetMapping("/availability")
    public List<VenueAvailabilityDto> getAllVenuesAvailability() {
        return venueService.getAllVenuesAvailability();
    }
}
