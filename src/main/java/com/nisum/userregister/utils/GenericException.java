package com.nisum.userregister.utils;

import org.springframework.http.HttpStatus;
import lombok.Data;

@Data
public class GenericException extends RuntimeException {
    private int statusCode;

    private HttpStatus status;
    
    public GenericException(String message, HttpStatus status) {
        super(message);
        this.statusCode = status.value();
        this.status = status;
    }
}
