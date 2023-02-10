package com.github.my.movie.tickets.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@RequiredArgsConstructor
@Builder
public class ErrorResponse {
    private final HttpStatus httpStatus;
    private final List<String> message;
}