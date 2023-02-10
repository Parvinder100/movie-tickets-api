package com.github.my.movie.tickets.dto;

import com.github.my.movie.tickets.enums.TicketType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class TicketFactoryTest {
    private TicketFactory ticketFactory = new TicketFactory();

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(ticketFactory, "adultTicketPrice", 25);
        ReflectionTestUtils.setField(ticketFactory, "seniorTicketDiscount", 30);
        ReflectionTestUtils.setField(ticketFactory, "teenTicketPrice", 12);
        ReflectionTestUtils.setField(ticketFactory, "childTicketPrice", 5);
    }

    @Test
    public void testCreateTicket_whenValidSeniorCustomer_returnValidSeniorTicket() {
        Ticket ticket = ticketFactory.createTicket(TransactionRequest.Customer.builder().age(65).name("John").build());
        Assertions.assertEquals(Ticket.builder().ticketType(TicketType.Senior).totalCost(17.5f).quantity(1).build(), ticket);
    }

    @Test
    public void testCreateTicket_whenValidAdultCustomerWith64Age_returnValidAdultTicket() {
        Ticket ticket = ticketFactory.createTicket(TransactionRequest.Customer.builder().age(64).name("John").build());
        Assertions.assertEquals(Ticket.builder().ticketType(TicketType.Adult).totalCost(25).quantity(1).build(), ticket);
    }

    @Test
    public void testCreateTicket_whenValidAdultCustomerWith18Age_returnValidAdultTicket() {
        Ticket ticket = ticketFactory.createTicket(TransactionRequest.Customer.builder().age(18).name("John").build());
        Assertions.assertEquals(Ticket.builder().ticketType(TicketType.Adult).totalCost(25).quantity(1).build(), ticket);
    }

    @Test
    public void testCreateTicket_whenValidTeenCustomerWith17Age_returnValidTeenTicket() {
        Ticket ticket = ticketFactory.createTicket(TransactionRequest.Customer.builder().age(17).name("John").build());
        Assertions.assertEquals(Ticket.builder().ticketType(TicketType.Teen).totalCost(12).quantity(1).build(), ticket);
    }

    @Test
    public void testCreateTicket_whenValidTeenCustomerWith11Age_returnValidTeenTicket() {
        Ticket ticket = ticketFactory.createTicket(TransactionRequest.Customer.builder().age(11).name("John").build());
        Assertions.assertEquals(Ticket.builder().ticketType(TicketType.Teen).totalCost(12).quantity(1).build(), ticket);
    }

    @Test
    public void testCreateTicket_whenValidChildCustomer_returnValidChildrenTicket() {
        Ticket ticket = ticketFactory.createTicket(TransactionRequest.Customer.builder().age(10).name("John").build());
        Assertions.assertEquals(Ticket.builder().ticketType(TicketType.Children).totalCost(5).quantity(1).build(), ticket);
    }
}