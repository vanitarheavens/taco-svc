package com.tacoloco.tacosvc.exception.advice;

import com.tacoloco.tacosvc.exception.CustomerNotFoundException;
import com.tacoloco.tacosvc.exception.OrderNotFoundException;
import com.tacoloco.tacosvc.exception.TacoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler({ TacoNotFoundException.class })
    public static ResponseEntity<String> handleTacoNotFoundException(HttpServletRequest request, TacoNotFoundException e) {
        String error = "Request failed due to TacoNotFoundException: " + e.getMessage() + " for URI " + request.getRequestURL();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ CustomerNotFoundException.class })
    public static ResponseEntity<String> handleCustomerNotFoundException(HttpServletRequest request, CustomerNotFoundException e) {
        String error = "Request failed due to CustomerNotFoundException: " + e.getMessage() + " for URI " + request.getRequestURL();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ OrderNotFoundException.class })
    public static ResponseEntity<String> handleOrderNotFoundException(HttpServletRequest request, OrderNotFoundException e) {
        String error = "Request failed due to OrderNotFoundException: " + e.getMessage() + " for URI " + request.getRequestURL();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
