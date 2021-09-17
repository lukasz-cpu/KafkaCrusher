package com.example.kafkacrusher.client.connection;

public class RegisterClientException extends RuntimeException {
    public RegisterClientException(String errorMessage) {
        super(errorMessage);
    }
}

