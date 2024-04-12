package org.ndrmicroservices.ticketservice.controller;

import org.ndrmicroservices.ticketservice.dto.TicketRequest;
import org.ndrmicroservices.ticketservice.model.Ticket;
import org.ndrmicroservices.ticketservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/{venueId}/bookings")
    public Ticket bookTickets(@PathVariable Long venueId, @RequestBody TicketRequest request) {
        return ticketService.bookTickets(venueId, request.getNumTickets());
    }

    @DeleteMapping("/{venueId}/bookings/{bookingId}")
    public void cancelBooking(@PathVariable Long venueId, @PathVariable Long bookingId) {
        ticketService.cancelBooking(bookingId);
    }
}
