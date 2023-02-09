package com.github.my.movie.tickets.discount;

import com.github.my.movie.tickets.dto.Ticket;
import com.github.my.movie.tickets.dto.TicketCart;
import com.github.my.movie.tickets.enums.TicketType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChildrenTicketDiscountHandler extends DiscountHandler {
    @Value("${ticket.discount.children.minimumChildCount}")
    private int minimumChildCount;
    @Value("${ticket.discount.children.discount}")
    private float discount;

    @Override
    public void applyDiscount(TicketCart ticketCart)  {
        Optional<Ticket> ticketOptional = ticketCart.getTickets().stream().filter(t -> TicketType.Children.equals(t.getTicketType())).findFirst();
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            if (ticket.getQuantity() >= minimumChildCount) {
                ticket.setTotalCost(ticket.getTotalCost() * (1 - discount));
            }
        }
        if (hasNext()) {
            nextDiscountHandler.applyDiscount(ticketCart);
        }
    }
}
