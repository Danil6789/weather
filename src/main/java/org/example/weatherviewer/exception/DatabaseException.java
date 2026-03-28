package org.example.weatherviewer.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable source) {
        super(message, source);
    }
}
