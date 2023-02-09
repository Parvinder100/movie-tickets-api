package com.github.my.movie.tickets.discount;

public class DiscountHandlerFactory {

    public static DiscountHandler createChildrenTicketDiscountHandler() {
        return new ChildrenTicketDiscountHandler();
    }
}
