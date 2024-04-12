package org.ndrmicroservices.venueservice.service;

import org.ndrmicroservices.venueservice.dto.VenueAvailabilityDto;
import org.ndrmicroservices.venueservice.model.Venue;
import org.ndrmicroservices.venueservice.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    @Autowired
    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public void updateVenueOccupancy(String message) {
        // Parse message and update venue occupancy accordingly
        // Example: venueId:capacity
        String[] parts = message.split(":");
        if (parts.length == 2) {
            Long venueId = Long.parseLong(parts[0]);
            int capacity = Integer.parseInt(parts[1]);
            // Update venue occupancy in the database
            venueRepository.updateOccupancy(venueId, capacity);
            // Example: venueRepository.updateOccupancy(venueId, capacity);
        }
    }

    public VenueAvailabilityDto getVenueAvailability(Long venueId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        int totalCapacity = venue.getCapacity();
        int currentOccupancy = venue.getCurrentOccupancy(); // Implement this method in Venue entity or repository

        int availableSeats = totalCapacity - currentOccupancy;

        return new VenueAvailabilityDto(venueId, availableSeats, totalCapacity);
    }

    public List<VenueAvailabilityDto> getAllVenuesAvailability() {
        List<Venue> venues = venueRepository.findAll();
        return venues.stream()
                .map(venue -> new VenueAvailabilityDto(venue.getId(), venue.getCapacity() - venue.getCurrentOccupancy(), venue.getCapacity()))
                .collect(Collectors.toList());
    }
}