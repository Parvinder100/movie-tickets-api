package com.github.my.movie.tickets.discount;

import com.github.my.movie.tickets.dto.TicketCart;

public abstract class DiscountHandler {
    protected DiscountHandler nextDiscountHandler;

    public abstract void applyDiscount(TicketCart ticketCart);
    public boolean hasNext() {
        return this.nextDiscountHandler != null;
    }
    public void setNextHandler(DiscountHandler nextDiscountHandler) {
        this.nextDiscountHandler = nextDiscountHandler;
    }
}
