package com.example.kafkacrusher.connection;

public class RegisterClientException extends RuntimeException {
    public RegisterClientException(String errorMessage) {
        super(errorMessage);
    }
}

