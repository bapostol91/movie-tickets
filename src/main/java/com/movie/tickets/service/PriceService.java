package com.movie.tickets.service;

import com.movie.tickets.config.PriceConfiguration;
import com.movie.tickets.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

/**
 * PriceService class that holds the logic of computing the total cost for each ticket based on the quantity and
 * price configuration that were set on application.properties
 */
@Service
@RequiredArgsConstructor
public class PriceService {

    private final PriceConfiguration priceConfiguration;


    /**
     * For each ticket computes the total cost based on the configuration file
     *
     * @param tickets
     */
    public void computePrices(Set<Ticket> tickets) {
        tickets.forEach(ticket -> ticket.setTotalCost(computePrice(ticket)));
    }

    private BigDecimal computePrice(Ticket ticket) {
        return switch (ticket.getTicketType()) {
            case Children -> computeChildrenPrice(ticket);
            case Teen -> computeTeenPrice(ticket);
            case Adult -> computeAdultPrice(ticket);
            default -> computeSeniorPrice(ticket);
        };
    }

    /**
     * Computes the senior price for a given ticket.
     *
     * @param ticket - input ticket object that holds the ticket quantity.
     * @return total cost for this ticket.
     */
    private BigDecimal computeSeniorPrice(Ticket ticket) {
        return getPrice(priceConfiguration.getSenior(),
                priceConfiguration.getSeniorDiscountApplyMinPerson(),
                priceConfiguration.getSeniorDiscountPercent(),
                ticket.getQuantity());
    }

    /**
     * Computes the adult price for a given ticket.
     *
     * @param ticket - input ticket object that holds the ticket quantity.
     * @return total cost for this ticket.
     */
    private BigDecimal computeAdultPrice(Ticket ticket) {
        return getPrice(priceConfiguration.getAdult(),
                priceConfiguration.getAdultDiscountApplyMinPerson(),
                priceConfiguration.getAdultDiscountPercent(),
                ticket.getQuantity());
    }

    /**
     * Computes the teen price for a given ticket.
     *
     * @param ticket - input ticket object that holds the ticket quantity.
     * @return total cost for this ticket.
     */
    private BigDecimal computeTeenPrice(Ticket ticket) {
        return getPrice(priceConfiguration.getTeen(),
                priceConfiguration.getTeenDiscountApplyMinPerson(),
                priceConfiguration.getTeenDiscountPercent(),
                ticket.getQuantity());
    }

    /**
     * Computes the children price for a given ticket.
     *
     * @param ticket - input ticket object that holds the ticket quantity.
     * @return total cost for this ticket.
     */
    private BigDecimal computeChildrenPrice(Ticket ticket) {
        return getPrice(priceConfiguration.getChildren(),
                priceConfiguration.getChildrenDiscountApplyMinPerson(),
                priceConfiguration.getChildrenDiscountPercent(),
                ticket.getQuantity());
    }


    /**
     * Reusable function shared with each TicketType that helps computing the price of the ticket.
     *
     * @param price              - configured price for a given TicketType.
     * @param minPerson          - min person to which a discount may be applied.
     * @param discountPercentage - percentage of discount that is subtracted from the configured price.
     * @param ticketQuantity     - the quantity of the given ticket.
     * @return total cost price for the given input.
     */
    private BigDecimal getPrice(BigDecimal price, int minPerson, double discountPercentage, int ticketQuantity) {
        if (minPerson > 0 && minPerson <= ticketQuantity) {
            price = price.subtract(price.multiply(BigDecimal.valueOf(discountPercentage)).divide(BigDecimal.valueOf(100)));
        }
        return price.multiply(BigDecimal.valueOf(ticketQuantity)).setScale(2);
    }
}
