package com.movie.tickets.controller;

import com.movie.tickets.model.TicketRequest;
import com.movie.tickets.model.TicketResponse;
import com.movie.tickets.service.TicketsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TicketsControllerTest {

    @Mock
    private TicketsService ticketsService;
    @Mock
    private TicketRequest ticketRequest;
    @Mock
    private TicketResponse ticketResponse;

    @InjectMocks
    private TicketsController target;

    @Test
    public void shouldCreateTicket() {
        when(ticketsService.createTickets(ticketRequest)).thenReturn(ticketResponse);
        assertThat(target.createTickets(ticketRequest)).isEqualTo(ticketResponse);
    }
}