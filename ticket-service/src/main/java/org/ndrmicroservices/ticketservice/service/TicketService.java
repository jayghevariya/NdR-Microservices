package org.ndrmicroservices.ticketservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ndrmicroservices.ticketservice.dto.BookingRequestDto;
import org.ndrmicroservices.ticketservice.dto.VenueAvailabilityDto;
import org.ndrmicroservices.ticketservice.model.Ticket;
import org.ndrmicroservices.ticketservice.repository.TicketRepository;
import org.springframework.stereotype.Service;


import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository bookingRepository;

    private final WebClient.Builder webClientBuilder;


    public Ticket bookTickets(Long venueId, int numTickets) {
        VenueAvailabilityDto venueAvailabilityDto = webClientBuilder.build().get()
                .uri("http://venue-management/venues/"+ venueId + "/availability")
                .retrieve()
                .bodyToMono(VenueAvailabilityDto.class)
                .block();

        if (venueAvailabilityDto.getAvailableSeats() < numTickets) {
            throw new RuntimeException("Not enough tickets available");
        }

        BookingRequestDto bookingReq = new BookingRequestDto(numTickets);
        String booking = webClientBuilder.build().post()
                .uri("http://venue-management/venues/"+ venueId + "/bookings")
                .bodyValue(bookingReq)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (booking == null) {
            throw new RuntimeException("Booking failed");
        }

        Ticket ticket = new Ticket();
        ticket.setVenueId(venueId);
        ticket.setNumTickets(numTickets);

        return bookingRepository.save(ticket);
    }

    public void cancelBooking(Long bookingId) {
        // Implement cancellation logic here
        // Retrieve booking data from the database
        // Cancel the booking and release reserved tickets
        // Update the database
    }
}
