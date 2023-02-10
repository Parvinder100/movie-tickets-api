package com.github.my.movie.tickets.controller;

import com.github.my.movie.tickets.dto.TicketCart;
import com.github.my.movie.tickets.dto.TransactionRequest;
import com.github.my.movie.tickets.service.MovieTicketsCostService;
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
public class MovieTicketsCostControllerTest {
    @InjectMocks
    private MovieTicketsCostController movieTicketsCostController;
    @Mock
    private MovieTicketsCostService movieTicketsCostService;
    @Test
    public void testGetTicketCost() {
        List<TransactionRequest.Customer> customerList = new ArrayList<>();
        customerList.add(TransactionRequest.Customer.builder().age(23).name("John").build());
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setCustomers(customerList);

        TicketCart expectedTicketCart = TicketCart.builder().build();

        when(movieTicketsCostService.getTicketCost(any(List.class))).thenReturn(expectedTicketCart);

        TicketCart ticketCart = movieTicketsCostController.getMovieCost(transactionRequest);

        Assertions.assertEquals(expectedTicketCart, ticketCart);
    }
}
