package org.example.weatherviewer.exception.common;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable source) {
        super(message, source);
    }
}
