package org.example.streams.validator;

public class InValidPasswordException extends RuntimeException {
    public InValidPasswordException(String message) {
        super(message);
    }
}
