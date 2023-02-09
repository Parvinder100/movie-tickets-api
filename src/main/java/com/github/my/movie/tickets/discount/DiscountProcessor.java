package com.github.my.movie.tickets.discount;

import com.github.my.movie.tickets.dto.Ticket;
import com.github.my.movie.tickets.dto.TicketCart;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiscountProcessor {

    private DiscountHandler discountHandler;

    public void applyDiscount(TicketCart ticketCart) {
        discountHandler.applyDiscount(ticketCart);
    }

    public void addHandler(DiscountHandler discountHandler) {
        if (this.discountHandler == null) {
            this.discountHandler = discountHandler;
        } else {
            discountHandler.setNextHandler(discountHandler);
        }
    }
}
