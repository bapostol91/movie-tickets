package com.movie.tickets.service;

import com.movie.tickets.model.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TicketsServiceTest {


    @Mock
    private PriceService priceService;

    @InjectMocks
    private TicketsService target;

    @ParameterizedTest
    @CsvSource({
            "John Doe,25,1,Adult,25.00",
            "John Doe,6,1,Children,5.00",
            "John Doe,14,1,Teen,12.00",
            "John Doe,70,1,Senior,17.50",
            "John Doe,25,2,Adult,25.00",
            "John Doe,6,3,Children,5.00",
            "John Doe,14,4,Teen,12.00",
            "John Doe,70,5,Senior,17.50",
    })
    public void shouldCreateTickets(String name, int age, int quantity, TicketType ticketType, BigDecimal cost) {
        long transactionId = 1L;
        TicketRequest ticketRequest = generateTicket(transactionId, name, age, quantity);
        BigDecimal totalCost = cost.multiply(BigDecimal.valueOf(quantity));

        doAnswer(invocation -> {
            Set<Ticket> ticket = invocation.getArgument(0);
            ticket.forEach(t -> t.setTotalCost(totalCost));
            return null;
        }).when(priceService).computePrices(any());

        TicketResponse ticketResponse = target.createTickets(ticketRequest);

        verify(priceService).computePrices(any());
        assertThat(ticketResponse).isNotNull();
        assertThat(ticketResponse.getId()).isEqualTo(transactionId);
        assertThat(ticketResponse.getTotalCost()).isEqualTo(totalCost);
        ticketResponse.getTickets().forEach(ticket -> {
            assertThat(ticket).isNotNull();
            assertThat(ticket.getQuantity()).isEqualTo(quantity);
            assertThat(ticket.getTicketType()).isEqualTo(ticketType);
            assertThat(ticket.getTotalCost()).isEqualTo(totalCost);
        });
    }

    private TicketRequest generateTicket(long transactionId, String name, int age, int quantity) {
        List<Customer> customers = generateCustomers(name, age, quantity);
        return TicketRequest.builder()
                .transactionId(transactionId)
                .customers(customers)
                .build();
    }

    private List<Customer> generateCustomers(String name, int age, int quantity) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            customers.add(Customer.builder()
                    .name(name)
                    .age(age)
                    .build());
        }
        return customers;
    }

}