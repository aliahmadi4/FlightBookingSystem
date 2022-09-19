package com.aerotravel.flightticketbooking.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityName, String message) {
        super(entityName + " | " + message);
    }

    public EntityNotFoundException(String entityName, String message, Throwable cause) {
        super(entityName + " | " + message, cause);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException() {
        super();
    }
}
