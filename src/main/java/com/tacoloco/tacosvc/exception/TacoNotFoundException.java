package com.tacoloco.tacosvc.exception;

public class TacoNotFoundException extends RuntimeException {
    public TacoNotFoundException(String message) {
        super(message);
    }
}
