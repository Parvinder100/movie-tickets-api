package com.github.my.movie.tickets.dto;

import com.github.my.movie.tickets.enums.TicketType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Component
public class TicketFactory {
    @Value("${ticket.price.adult}")
    private float adultTicketPrice;
    @Value("${ticket.price.discount.senior}")
    private float seniorTicketDiscount;
    @Value("${ticket.price.teen}")
    private float teenTicketPrice;
    @Value("${ticket.price.child}")
    private float childTicketPrice;
    private static Map<TicketType, Ticket> ticketMap = new HashMap<>();

    public Ticket createTicket(TransactionRequest.Customer customer) {
        if (customer.getAge() > 65) {
            return fetchTicket(TicketType.Senior, () -> {return adultTicketPrice * (100-seniorTicketDiscount)/100;});
        }
        if (customer.getAge() > 18) {
            return fetchTicket(TicketType.Adult, () -> {return adultTicketPrice;});
        }
        if (customer.getAge() > 11) {
            return fetchTicket(TicketType.Teen, () -> {return teenTicketPrice;});
        }
        return fetchTicket(TicketType.Children, () -> {return childTicketPrice;});
    }

    private static Ticket fetchTicket(TicketType ticketType, Supplier<Float> priceSupplier) {
        Ticket ticket = ticketMap.get(ticketType);
        if (ticket == null) {
            ticket = Ticket.builder().ticketType(ticketType).totalCost(priceSupplier.get()).build();
            ticketMap.put(ticketType, ticket);
        }
        return ticket;
    }
}
