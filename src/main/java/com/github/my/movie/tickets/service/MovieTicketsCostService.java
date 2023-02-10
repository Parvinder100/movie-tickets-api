package com.github.my.movie.tickets.service;

import com.github.my.movie.tickets.discount.DiscountHandlerFactory;
import com.github.my.movie.tickets.discount.DiscountProcessor;
import com.github.my.movie.tickets.dto.Ticket;
import com.github.my.movie.tickets.dto.TicketCart;
import com.github.my.movie.tickets.dto.TicketFactory;
import com.github.my.movie.tickets.dto.TransactionRequest;
import com.github.my.movie.tickets.entity.TicketCartEntity;
import com.github.my.movie.tickets.entity.TicketEntity;
import com.github.my.movie.tickets.repository.TicketCartRepository;
import com.github.my.movie.tickets.repository.TicketRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieTicketsCostService {
    private int transactionId = 1;
    @Autowired
    private TicketFactory ticketFactory;
    @Autowired
    private DiscountHandlerFactory discountHandlerFactory;
    @Autowired
    private DiscountProcessor discountProcessor;
    @Autowired
    private TicketCartRepository ticketCartRepository;
    @Autowired
    private TicketRepository ticketRepository;

    public TicketCart getTicketCost(List<TransactionRequest.Customer> customerList) {
        TicketCart ticketCart = TicketCart.builder().build();
        customerList.stream().forEach(customer -> {
            Ticket ticket = ticketFactory.createTicket(customer);
            ticketCart.addTicket(ticket);
        });

        discountProcessor.applyDiscount(ticketCart);

        //update the final total after applying discounts
        ticketCart.updateTotalCostOfTicketCart();

        if (!ObjectUtils.isEmpty(ticketCart.getTickets())) {
            //Storing the ticket cart in db. Entity id is used as transaction id.
            TicketCartEntity ticketCartEntity = saveTicketCart(ticketCart);
            ticketCart.setTransactionId(ticketCartEntity.getId());
        }
        return ticketCart;
    }

    private TicketCartEntity saveTicketCart(TicketCart ticketCart) {
        TicketCartEntity ticketCartEntity = TicketCartEntity.builder().totalCost(ticketCart.getTotalCost()).build();
        List<TicketEntity> ticketEntityList = new ArrayList<>();
        for (Ticket ticket : ticketCart.getTickets()) {
            ticketEntityList.add(TicketEntity.builder().ticketType(ticket.getTicketType()).quantity(ticket.getQuantity())
                    .totalCost(ticket.getTotalCost()).ticketCartEntity(ticketCartEntity).build());
        }
        ticketCartEntity.setTicketEntityList(ticketEntityList);
        TicketCartEntity persistedTicketCartEntity = ticketCartRepository.save(ticketCartEntity);
        ticketRepository.saveAll(ticketEntityList);

       return persistedTicketCartEntity;
    }

    @PostConstruct
    public void addDiscountProcessor() {
        //Add Children ticket discount
        discountProcessor.addHandler(discountHandlerFactory.getChildrenTicketDiscountHandler());
    }
}