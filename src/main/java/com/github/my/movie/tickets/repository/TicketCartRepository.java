package com.github.my.movie.tickets.repository;

import com.github.my.movie.tickets.entity.TicketCartEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketCartRepository extends CrudRepository<TicketCartEntity, String> {
}