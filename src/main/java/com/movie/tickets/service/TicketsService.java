package com.movie.tickets.service;

import com.movie.tickets.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Ticket service that encapsulate the entire domain logic for creating tickets.
 */
@Service
@RequiredArgsConstructor
public class TicketsService {

    private final PriceService priceService;

    /**
     * Creates a ticket response with all the tickets sorted by Tickets Type.
     * <p>
     * (!) Note that the transactionId is simply returned back.
     *
     * @param ticketRequest - ticker requests wit the entire list of customers.
     * @return a Ticket response with all the ticket types along with a total cost per ticket
     */
    public TicketResponse createTickets(TicketRequest ticketRequest) {
        Set<Ticket> tickets = getTickets(ticketRequest.getCustomers());
        priceService.computePrices(tickets);
        return TicketResponse.builder()
                .id(ticketRequest.getTransactionId())
                .tickets(tickets)
                .totalCost(getTotalCost(tickets))
                .build();
    }

    /**
     * Sums all the ticket prices into a single BigDecimal Value with 2 decimals.
     *
     * @param tickets - input tickets
     * @return a sum with total cost for the entire set of tickets.
     */
    private BigDecimal getTotalCost(Set<Ticket> tickets) {
        return tickets.stream().map(Ticket::getTotalCost).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2);
    }

    /**
     * Get the tickets without prices
     *
     * @param customers - list of customers
     * @return list of tickets
     */
    private Set<Ticket> getTickets(List<Customer> customers) {
        Map<TicketType, Ticket> ticketMap = new HashMap<>();
        for (Customer customer : customers) {
            TicketType ticketType = TicketType.getValueOf(customer.getAge());
            if (ticketMap.containsKey(ticketType)) {
                ticketMap.get(ticketType).increaseQuantity();
            } else {
                ticketMap.put(ticketType, Ticket.builder()
                        .ticketType(ticketType)
                        .quantity(1)
                        .build());
            }
        }
        return new HashSet<>(ticketMap.values());
    }
}
