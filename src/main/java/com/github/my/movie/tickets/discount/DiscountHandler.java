package com.github.my.movie.tickets.discount;

import com.github.my.movie.tickets.dto.Ticket;
import com.github.my.movie.tickets.dto.TicketCart;

import java.util.List;

public abstract class DiscountHandler {
    private DiscountHandler nextDiscountHandler;

    public abstract void applyDiscount(TicketCart ticketCart);

    public boolean hasNext() {
        return this.nextDiscountHandler != null;
    }

    public void setNextHandler(DiscountHandler nextDiscountHandler) {
        this.nextDiscountHandler = nextDiscountHandler;
    }
}
