package org.ndrmicroservices.venueservice.service;

import lombok.RequiredArgsConstructor;
import org.ndrmicroservices.venueservice.dto.ConsumeObject;
import org.ndrmicroservices.venueservice.dto.VenueAvailabilityDto;
import org.ndrmicroservices.venueservice.model.Venue;
import org.ndrmicroservices.venueservice.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;

    public void updateVenueOccupancy(ConsumeObject message) {

        Venue venue = venueRepository.findById(message.getVenueId())
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        int netCount = message.getPersonInCount() - message.getPersonOutCount();

        if (venue.getCurrentOccupancy() + netCount < 0 || venue.getCurrentOccupancy() + netCount > venue.getCapacity()) {
            throw new RuntimeException("Invalid occupancy count");
        }

        venue.setCurrentOccupancy(venue.getCurrentOccupancy() + netCount);

        venueRepository.save(venue);
    }

    public VenueAvailabilityDto getVenueAvailability(Long venueId) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        int totalCapacity = venue.getCapacity();
        int currentOccupancy = venue.getCurrentOccupancy();

        int availableSeats = currentOccupancy;

        return new VenueAvailabilityDto(venueId, availableSeats, totalCapacity);
    }

    public List<VenueAvailabilityDto> getAllVenuesAvailability() {
        List<Venue> venues = venueRepository.findAll();
        return venues.stream()
                .map(venue -> new VenueAvailabilityDto(venue.getId(), venue.getCapacity() - venue.getCurrentOccupancy(), venue.getCapacity()))
                .collect(Collectors.toList());
    }

    public String bookTickets(Long venueId, int numTickets) {
        Venue venue = venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        int availableSeats = venue.getCurrentOccupancy();
        if (availableSeats < numTickets) {
            throw new RuntimeException("Not enough tickets available");
        }

        venue.setCurrentOccupancy(venue.getCurrentOccupancy() + numTickets);
        venueRepository.save(venue);

        return "Tickets booked successfully";
    }
}