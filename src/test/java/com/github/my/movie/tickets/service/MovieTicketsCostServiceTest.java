package com.github.my.movie.tickets.service;

import com.github.my.movie.tickets.discount.DiscountHandlerFactory;
import com.github.my.movie.tickets.discount.DiscountProcessor;
import com.github.my.movie.tickets.dto.Ticket;
import com.github.my.movie.tickets.dto.TicketCart;
import com.github.my.movie.tickets.dto.TicketFactory;
import com.github.my.movie.tickets.dto.TransactionRequest;
import com.github.my.movie.tickets.entity.TicketCartEntity;
import com.github.my.movie.tickets.enums.TicketType;
import com.github.my.movie.tickets.repository.TicketCartRepository;
import com.github.my.movie.tickets.repository.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieTicketsCostServiceTest {
    @InjectMocks
    private MovieTicketsCostService movieTicketsCostService;
    @Mock
    private TicketFactory ticketFactory;
    @Mock
    private DiscountHandlerFactory discountHandlerFactory;
    @Mock
    private DiscountProcessor discountProcessor;
    @Mock
    private TicketCartRepository ticketCartRepository;
    @Mock
    private TicketRepository ticketRepository;

    @Test
    public void testGetTicketCost_whenValidCustomerList_returnAValidTicketCart() {
        List<TransactionRequest.Customer> customerList = new ArrayList<>();
        customerList.add(TransactionRequest.Customer.builder().age(5).name("John").build());
        customerList.add(TransactionRequest.Customer.builder().age(8).name("Mike").build());
        when(ticketFactory.createTicket(any(TransactionRequest.Customer.class)))
                .thenReturn(Ticket.builder().ticketType(TicketType.Children).totalCost(20f).build())
                .thenReturn(Ticket.builder().ticketType(TicketType.Children).totalCost(20f).build());
        when(ticketCartRepository.save(any(TicketCartEntity.class))).thenReturn(TicketCartEntity.builder().id(2).build());

        TicketCart ticketCart = movieTicketsCostService.getTicketCost(customerList);
        Assertions.assertEquals(1, ticketCart.getTickets().size());
        Assertions.assertEquals(2, ticketCart.getTransactionId());
        Assertions.assertEquals(TicketType.Children, ticketCart.getTickets().stream().findFirst().get().getTicketType());
        Assertions.assertEquals(2, ticketCart.getTickets().stream().findFirst().get().getQuantity());
        Assertions.assertEquals(40, ticketCart.getTickets().stream().findFirst().get().getTotalCost(), 0.0f);
    }

    @Test
    public void testGetTicketCost_whenEmptyCustomerList_returnAValidTicketCart() {
        List<TransactionRequest.Customer> customerList = new ArrayList<>();
        TicketCart ticketCart = movieTicketsCostService.getTicketCost(customerList);
        Assertions.assertEquals(0, ticketCart.getTickets().size());
        Assertions.assertEquals(0, ticketCart.getTotalCost(), 0.0f);
    }
}