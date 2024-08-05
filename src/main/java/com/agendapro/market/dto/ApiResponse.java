package com.agendapro.market.dto;


import org.springframework.http.HttpStatus;


public class ApiResponse extends Response {
    public ApiResponse(String message, HttpStatus status) {
        super(message, status);
    }
}