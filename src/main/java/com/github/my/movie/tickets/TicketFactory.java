package com.github.my.movie.tickets;

import com.github.my.movie.tickets.dto.Ticket;
import com.github.my.movie.tickets.enums.TicketType;
import com.github.my.movie.tickets.dto.TransactionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TicketFactory {

    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${ticket.price.adult}")
    private int adultTicketPrice;
    @Value("${ticket.price.discount.senior}")
    private float seniorTicketDiscount;
    @Value("${ticket.price.teen}")
    private float teenTicketPrice;
    @Value("${ticket.price.child}")
    private float childTicketPrice;

    public Ticket createTicket(TransactionRequest.Customer customer) {
        if (customer.getAge() > 65) {
            return Ticket.builder().ticketType(TicketType.Senior)
                    .price(adultTicketPrice * (100-seniorTicketDiscount)/100)
                    .build();
        }

        if (customer.getAge() > 18) {
            return Ticket.builder().ticketType(TicketType.Adult)
                    .price(adultTicketPrice)
                    .build();
        }

        if (customer.getAge() > 11) {
            return Ticket.builder().ticketType(TicketType.Teen)
                    .price(teenTicketPrice)
                    .build();
        }

        return Ticket.builder().ticketType(TicketType.Children)
                .price(childTicketPrice)
                .build();
    }
}
