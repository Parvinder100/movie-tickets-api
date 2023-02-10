package com.github.my.movie.tickets.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class TransactionRequest {
    List<Customer> customers;

    @Data
    @Builder
    public static class Customer {
        private String name;
        private int age;
    }
}
