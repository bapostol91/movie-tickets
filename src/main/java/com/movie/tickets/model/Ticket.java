package com.movie.tickets.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    private TicketType ticketType;
    private int quantity = 0;
    private BigDecimal totalCost;

    public void increaseQuantity() {
        this.quantity++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        return ticketType != null && ticketType.equals(((Ticket) o).getTicketType());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
