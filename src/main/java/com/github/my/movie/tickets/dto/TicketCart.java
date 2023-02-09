package com.github.my.movie.tickets.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.my.movie.tickets.controller.CustomFloatSerializer;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class TicketCart {
    private int transactionId;
    @Builder.Default
    private Set<Ticket> tickets = new TreeSet<>();
    @JsonSerialize(using = CustomFloatSerializer.class)
    private float totalCost;

    public void addTicket(Ticket ticket) {
        Optional<Ticket> existingTicketOptional = tickets.stream().filter(t -> ticket.getTicketType().equals(t.getTicketType())).findFirst();
        if (existingTicketOptional.isPresent()) {
            Ticket existingTicket = existingTicketOptional.get();
            existingTicket.setQuantity(existingTicket.getQuantity() + ticket.getQuantity());
            existingTicket.setTotalCost(existingTicket.getTotalCost() + ticket.getTotalCost());
        } else {
            tickets.add(ticket.toBuilder().build());
        }
    }

    public void updateTotalCostOfTicketCart() {
        this.setTotalCost((float) this.tickets.stream().mapToDouble(t -> t.getTotalCost()).sum());
    }
}
