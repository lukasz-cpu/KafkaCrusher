package com.example.kafkacrusher.connection.register;

public class RegisterClientException extends RuntimeException {
    public RegisterClientException(String errorMessage) {
        super(errorMessage);
    }
}

