package com.movie.tickets.service;

import com.movie.tickets.config.PriceConfiguration;
import com.movie.tickets.model.Ticket;
import com.movie.tickets.model.TicketType;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    PriceConfiguration priceConfiguration;

    @InjectMocks
    private PriceService target;

    @ParameterizedTest
    @CsvSource({
            "Adult,1,25.00",
            "Adult,2,50.00"
    })
    public void shouldComputePriceForAdult(TicketType ticketType, int quantity, BigDecimal expectedCost) {
        when(priceConfiguration.getAdult()).thenReturn(BigDecimal.valueOf(25));
        when(priceConfiguration.getAdultDiscountApplyMinPerson()).thenReturn(0);
        when(priceConfiguration.getAdultDiscountPercent()).thenReturn((double) 0);
        verifyPrices(ticketType, quantity, expectedCost);
    }

    @ParameterizedTest
    @CsvSource({
            "Teen,1,12.00",
            "Teen,2,24.00",
    })
    public void shouldComputePriceForTeen(TicketType ticketType, int quantity, BigDecimal expectedCost) {
        when(priceConfiguration.getTeen()).thenReturn(BigDecimal.valueOf(12));
        when(priceConfiguration.getTeenDiscountApplyMinPerson()).thenReturn(0);
        when(priceConfiguration.getTeenDiscountPercent()).thenReturn((double) 0);
        verifyPrices(ticketType, quantity, expectedCost);
    }

    @ParameterizedTest
    @CsvSource({
            "Children,1,12.00",
            "Children,3,27.00",
    })
    public void shouldComputePriceForChildren(TicketType ticketType, int quantity, BigDecimal expectedCost) {
        when(priceConfiguration.getChildren()).thenReturn(BigDecimal.valueOf(12));
        when(priceConfiguration.getChildrenDiscountApplyMinPerson()).thenReturn(3);
        when(priceConfiguration.getChildrenDiscountPercent()).thenReturn((double) 25);
        verifyPrices(ticketType, quantity, expectedCost);
    }

    @ParameterizedTest
    @CsvSource({
            "Senior,1,17.50",
            "Senior,2,35.00",
    })
    public void shouldComputePriceForSenior(TicketType ticketType, int quantity, BigDecimal expectedCost) {
        when(priceConfiguration.getSenior()).thenReturn(BigDecimal.valueOf(25));
        when(priceConfiguration.getSeniorDiscountApplyMinPerson()).thenReturn(1);
        when(priceConfiguration.getSeniorDiscountPercent()).thenReturn((double) 30);
        verifyPrices(ticketType, quantity, expectedCost);
    }


    private void verifyPrices(TicketType ticketType, int quantity, BigDecimal expectedCost) {
        Ticket ticket = Ticket.builder()
                .ticketType(ticketType)
                .quantity(quantity)
                .build();

        HashSet<Ticket> tickets = new HashSet<>() {{
            add(ticket);
        }};
        target.computePrices(tickets);
        tickets.forEach(t -> assertEquals(expectedCost, t.getTotalCost()));
    }
}