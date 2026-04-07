package org.example.weatherviewer.auth.session.exception;

public class SessionAlreadyExistsException extends RuntimeException {
    public SessionAlreadyExistsException(String message) {
        super(message);
    }
}
