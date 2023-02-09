package com.github.my.movie.tickets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name="ticket_cart")
public class TicketCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="total_cost")
    private float totalCost;
    @OneToMany(mappedBy="ticketCartEntity")
    List<TicketEntity> ticketEntityList;
}
