package com.swisscom.demo.orderservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
public class UnavailableException extends RuntimeException {

    public UnavailableException(String message){
        super(message);
    }
}
