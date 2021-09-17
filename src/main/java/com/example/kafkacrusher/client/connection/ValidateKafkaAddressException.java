package com.example.kafkacrusher.client.connection;

public class ValidateKafkaAddressException extends RuntimeException {
    public ValidateKafkaAddressException(String errorMessage) {
        super(errorMessage);
    }
}