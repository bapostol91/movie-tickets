package com.movie.tickets.controller;

import com.movie.tickets.model.TicketRequest;
import com.movie.tickets.model.TicketResponse;
import com.movie.tickets.service.TicketsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles tickets API.
 * Holds the endpoint for creating a ticket for a given list of customers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ticket")
public class TicketsController {
    private final Logger LOGGER = LoggerFactory.getLogger(TicketsController.class);
    private final TicketsService ticketsService;

    @PostMapping("/create")
    public TicketResponse createTickets(@RequestBody TicketRequest ticketRequest) {
        LOGGER.info("Create tickets for the following request {}", ticketRequest);
        return ticketsService.createTickets(ticketRequest);
    }
}
