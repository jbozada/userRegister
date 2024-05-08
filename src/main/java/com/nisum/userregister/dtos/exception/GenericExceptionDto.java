package com.nisum.userregister.dtos.exception;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenericExceptionDto {

    private int statusCode;
    private String message;
}
