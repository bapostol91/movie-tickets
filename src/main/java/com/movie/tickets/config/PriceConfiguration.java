package com.movie.tickets.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Price Configuration for each individual type.
 * Each discount is configurable in applciation.properties.
 * Holds the data prefixed by `ticket.price`.
 */
@Component
@Data
@ConfigurationProperties(prefix = "ticket.price")
public class PriceConfiguration {
    private BigDecimal adult;
    private double adultDiscountPercent;
    private int adultDiscountApplyMinPerson;
    private BigDecimal teen;
    private double teenDiscountPercent;
    private int teenDiscountApplyMinPerson;
    private BigDecimal children;
    private double childrenDiscountPercent;
    private int childrenDiscountApplyMinPerson;
    private BigDecimal senior;
    private double seniorDiscountPercent;
    private int seniorDiscountApplyMinPerson;
}
