package com.github.my.movie.tickets.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.my.movie.tickets.enums.TicketType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TicketCart {
    private int transactionId;
    private List<Ticket> tickets;
    @JsonSerialize(using = CustomFloatSerializer.class)
    private float totalCost;

    public void addTicket(Ticket ticket) {
        Ticket existingTicket = tickets.stream().filter(t -> ticket.getTicketType().equals(t.getTicketType())).findFirst().get();
        if (existingTicket == null) {
            tickets.add(ticket.toBuilder().build());
        } else {
            existingTicket.setQuantity(existingTicket.getQuantity() + ticket.getQuantity());
            existingTicket.setTotalCost(existingTicket.getTotalCost() + ticket.getTotalCost());
        }
    }

    @Data
    @Builder
    public static class TicketGroup {
        private TicketType ticketType;
        private int quantity;
        @JsonSerialize(using = CustomFloatSerializer.class)
        private float totalCost;
    }
}
