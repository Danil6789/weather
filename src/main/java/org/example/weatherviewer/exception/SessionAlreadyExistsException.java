package org.example.weatherviewer.exception;

public class SessionAlreadyExistsException extends RuntimeException {
    public SessionAlreadyExistsException(String message) {
        super(message);
    }
}
