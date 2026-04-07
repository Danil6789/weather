package org.example.weatherviewer.exception.session;

public class SessionAlreadyExistsException extends RuntimeException {
    public SessionAlreadyExistsException(String message) {
        super(message);
    }
}
