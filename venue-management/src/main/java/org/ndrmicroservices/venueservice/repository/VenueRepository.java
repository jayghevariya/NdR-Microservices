package org.ndrmicroservices.venueservice.repository;

import org.ndrmicroservices.venueservice.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    void updateOccupancy(Long venueId, int capacity);
}

