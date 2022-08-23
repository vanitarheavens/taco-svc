package com.tacoloco.tacosvc.exception;

public class NotValidInputException extends RuntimeException {
    public NotValidInputException(String message) {
        super(message);
    }
}

