package com.github.my.movie.tickets.discount;

import com.github.my.movie.tickets.dto.Ticket;
import com.github.my.movie.tickets.dto.TicketCart;
import com.github.my.movie.tickets.enums.TicketType;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class ChildrenTicketDiscountHandlerTest {
    private ChildrenTicketDiscountHandler childrenTicketDiscountHandler = new ChildrenTicketDiscountHandler();

    @Test
    public void testApplyDiscount_whenValidConditions_shouldApplyDiscount() {
        TicketCart ticketCart = TicketCart.builder().build();
        ticketCart.addTicket(Ticket.builder().ticketType(TicketType.Children).totalCost(20f).build());
        ticketCart.addTicket(Ticket.builder().ticketType(TicketType.Children).totalCost(20f).build());
        ticketCart.addTicket(Ticket.builder().ticketType(TicketType.Children).totalCost(20f).build());
        ticketCart.addTicket(Ticket.builder().ticketType(TicketType.Senior).totalCost(20f).build());
        ticketCart.addTicket(Ticket.builder().ticketType(TicketType.Teen).totalCost(20f).build());
        ticketCart.addTicket(Ticket.builder().ticketType(TicketType.Adult).totalCost(20f).build());

        ReflectionTestUtils.setField(childrenTicketDiscountHandler, "minimumChildCount", 3);
        ReflectionTestUtils.setField(childrenTicketDiscountHandler, "discount", 40);

        childrenTicketDiscountHandler.applyDiscount(ticketCart);
        Ticket childrenTicket = ticketCart.getTickets().stream().filter(t -> TicketType.Children.equals(t.getTicketType())).findFirst().get();
        Assert.assertEquals(36f, childrenTicket.getTotalCost(), 0.0f);
    }

    @Test
    public void testApplyDiscount_whenInValidConditions_shouldNotApplyDiscount() {
        TicketCart ticketCart = TicketCart.builder().build();
        ticketCart.addTicket(Ticket.builder().ticketType(TicketType.Children).totalCost(20f).build());
        ticketCart.addTicket(Ticket.builder().ticketType(TicketType.Children).totalCost(20f).build());
        ticketCart.addTicket(Ticket.builder().ticketType(TicketType.Senior).totalCost(20f).build());
        ticketCart.addTicket(Ticket.builder().ticketType(TicketType.Teen).totalCost(20f).build());
        ticketCart.addTicket(Ticket.builder().ticketType(TicketType.Adult).totalCost(20f).build());

        ReflectionTestUtils.setField(childrenTicketDiscountHandler, "minimumChildCount", 3);
        ReflectionTestUtils.setField(childrenTicketDiscountHandler, "discount", 40);

        childrenTicketDiscountHandler.applyDiscount(ticketCart);
        Ticket childrenTicket = ticketCart.getTickets().stream().filter(t -> TicketType.Children.equals(t.getTicketType())).findFirst().get();
        Assert.assertEquals(40, childrenTicket.getTotalCost(), 0.0f);
    }
}