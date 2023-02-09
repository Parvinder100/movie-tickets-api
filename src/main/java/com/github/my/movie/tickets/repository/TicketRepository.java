package com.github.my.movie.tickets.repository;

import com.github.my.movie.tickets.entity.TicketEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<TicketEntity, String> {
}