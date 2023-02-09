package com.github.my.movie.tickets.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.my.movie.tickets.enums.TicketType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Ticket {
    private TicketType ticketType;
    private int quantity;
    @JsonSerialize(using = CustomFloatSerializer.class)
    private float totalCost;

}
