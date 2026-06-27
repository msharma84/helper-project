package org.example.streams.validator;

public class InCorrectEmailException extends RuntimeException {
    public InCorrectEmailException(String message) {
        super(message);
    }
}
