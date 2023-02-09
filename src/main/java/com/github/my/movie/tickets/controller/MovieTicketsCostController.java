package com.github.my.movie.tickets.controller;

import com.github.my.movie.tickets.dto.TransactionRequest;
import com.github.my.movie.tickets.dto.TicketCart;
import com.github.my.movie.tickets.service.MovieTicketsCostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MovieTicketsCostController {

    @Autowired
    private MovieTicketsCostService movieTicketsCostService;

    /**
     */
    @PostMapping("/getMovieCost")
    public TicketCart getMovieCost(@RequestBody TransactionRequest transactionRequest) {

        TicketCart transactionResponse = movieTicketsCostService.getTicketCost(transactionRequest.getCustomers());
        log.info("Request for a suburb for a postcode=");
        return transactionResponse;
    }
}