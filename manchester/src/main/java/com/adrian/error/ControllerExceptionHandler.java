package com.adrian.error;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(FeignException.UnprocessableEntity.class)
    public ResponseEntity<ErrorResponse> handleUnprocessableException(FeignException exception, HttpServletResponse response){
        String msg = "Time has already been booked";
        return buildResponseEntity(new ErrorResponse(exception.status(), msg));
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(FeignException exception, HttpServletResponse response){
        String msg = "Bad request parameter: id";
        return buildResponseEntity(new ErrorResponse(exception.status(), msg));
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse errorResponse){
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getCode()));
    }

}
