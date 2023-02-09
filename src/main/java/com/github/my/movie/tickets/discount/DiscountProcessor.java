package com.github.my.movie.tickets.discount;

import com.github.my.movie.tickets.dto.TicketCart;
import org.springframework.stereotype.Component;

@Component
public class DiscountProcessor {
    private DiscountHandler firstDiscountHandler;
    private DiscountHandler lastDiscountHandler;
    public void applyDiscount(TicketCart ticketCart) {
        firstDiscountHandler.applyDiscount(ticketCart);
    }

    public void addHandler(DiscountHandler discountHandler) {
        if (this.firstDiscountHandler == null) {
            this.firstDiscountHandler = discountHandler;
            this.lastDiscountHandler = discountHandler;
        } else {
            lastDiscountHandler.setNextHandler(discountHandler);
            lastDiscountHandler = discountHandler;
        }
    }
}
