package com.nisum.userregister.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nisum.userregister.dtos.exception.GenericExceptionDto;
import com.nisum.userregister.utils.GenericException;


@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = GenericException.class)
    public ResponseEntity<GenericExceptionDto> passwordExceptionHandler(GenericException ex){
        GenericExceptionDto genericExceptionDto = GenericExceptionDto.builder().statusCode(ex.getStatusCode()).message(ex.getMessage()).build();
        return new ResponseEntity<>(genericExceptionDto, ex.getStatus());
    }
    
}
