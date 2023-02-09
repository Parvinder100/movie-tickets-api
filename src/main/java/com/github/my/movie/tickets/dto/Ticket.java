package com.github.my.movie.tickets.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.my.movie.tickets.controller.CustomFloatSerializer;
import com.github.my.movie.tickets.enums.TicketType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Ticket implements Comparable<Ticket>{
    private TicketType ticketType;
    @Builder.Default
    private int quantity = 1;
    @JsonSerialize(using = CustomFloatSerializer.class)
    private float totalCost;

    @Override
    public int compareTo(Ticket ticket) {
        return this.ticketType.toString().compareTo(ticket.getTicketType().toString());
    }
}
