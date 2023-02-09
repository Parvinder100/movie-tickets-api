package com.github.my.movie.tickets.service;

import com.github.my.movie.tickets.TicketFactory;
import com.github.my.movie.tickets.controller.ResponseHandler;
import com.github.my.movie.tickets.discount.DiscountHandlerFactory;
import com.github.my.movie.tickets.discount.DiscountProcessor;
import com.github.my.movie.tickets.dto.Ticket;
import com.github.my.movie.tickets.dto.TicketCart;
import com.github.my.movie.tickets.dto.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieTicketsService {
    private int transactionId = 1;
    @Autowired
    private TicketFactory ticketFactory;
    @Autowired
    private DiscountProcessor discountProcessor;

    public TicketCart ticket(List<TransactionRequest.Customer> customerList) {

//        TicketCart ticketCart1 = TicketCart.builder().transactionId(transactionId++).build();
//        customerList.stream().forEach(customer -> {
//            Ticket ticket = ticketFactory.createTicket(customer);
//        });

        List<Ticket> ticketList = customerList.stream().map(c -> ticketFactory.createTicket(c)).collect(Collectors.toList());
        TicketCart ticketCart = this.createTicketCart(ticketList);
        //Add Children ticket discount
        discountProcessor.addHandler(DiscountHandlerFactory.createChildrenTicketDiscountHandler());
        discountProcessor.applyDiscount(ticketCart);

        // update the final total after applying discounts
        this.updateFinalTotalOfTicketCart(ticketCart);
        return ticketCart;
    }

    private TicketCart createTicketCart(List<Ticket> ticketList) {
        List<TicketCart.TicketGroup> ticketResponseList = ticketList.stream().collect(Collectors.groupingBy(Ticket::getTicketType, Collectors.toList()))
                .values().stream()
                .map(group -> TicketCart.TicketGroup.builder()
                        .ticketType(group.get(0).getTicketType())
                        .totalCost((float) group.stream().mapToDouble(e -> e.getPrice()).sum())
                        .quantity((int) group.stream().count()).build())
                .collect(Collectors.toList());

        ticketResponseList.sort(Comparator.comparing(t -> t.getTicketType().toString()));

        return TicketCart.builder().tickets(ticketResponseList)
                .transactionId(transactionId++)
                .totalCost((float) ticketResponseList.stream().mapToDouble(t -> t.getTotalCost()).sum())
                .build();
    }

    private void updateFinalTotalOfTicketCart(TicketCart ticketCart) {
        ticketCart.setTotalCost((float) ticketCart.getTickets().stream().mapToDouble(t -> t.getTotalCost()).sum());
    }
}