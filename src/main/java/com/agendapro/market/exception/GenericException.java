package com.agendapro.market.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends HandlerException{
    public GenericException(String message, HttpStatus status, LocalDateTime timestamp) {
        super(message, status, timestamp);
    }
}