package com.movie.tickets.model;

/**
 * Enum class for ticket type based on the age of the customer.
 */
public enum TicketType {
    Adult, Senior, Teen, Children;

    /**
     * Determine the customer age based on its age.
     * <b>>Adult</b: For customers 18 years and older but less than 65 years old.
     * <b>Senior</b>: For customers 65 years and older.
     * <b>Teen</b>: For customers 11 years and older but less than 18 years old.
     * <b>Children</b>: For customers less than 11 years of age.
     *
     * @param age input age of the individual
     * @return the TicketType based on the customer age.
     */
    public static TicketType getValueOf(int age) {
        if (age < 11) {
            return Children;
        } else if (age < 18) {
            return Teen;
        } else if (age < 65) {
            return Adult;
        }
        return Senior;

    }
}
