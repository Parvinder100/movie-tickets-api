package com.github.my.movie.tickets.entity;

import com.github.my.movie.tickets.enums.TicketType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name="ticket")
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Enumerated(EnumType.STRING)
    @Column(name="ticket_type", nullable=false)
    private TicketType ticketType;
    @Column(name="quantity")
    private int quantity = 1;
    @Column(name="total_cost")
    private float totalCost;
    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private TicketCartEntity ticketCartEntity;
}
