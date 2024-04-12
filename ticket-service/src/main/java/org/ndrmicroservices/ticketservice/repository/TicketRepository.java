package org.ndrmicroservices.ticketservice.repository;

import org.ndrmicroservices.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
