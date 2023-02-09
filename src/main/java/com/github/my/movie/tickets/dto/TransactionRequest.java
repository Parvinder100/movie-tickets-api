package com.github.my.movie.tickets.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TransactionRequest {
    List<Customer> customers;

    @Data
    @Builder
    public static class Customer {
        private String name;
        private int age;
    }
}
