package com.example.tiktok.exceptions;

public class CreateFailureException extends RuntimeException {
    public CreateFailureException(String message) {
        super(message);
    }
}
