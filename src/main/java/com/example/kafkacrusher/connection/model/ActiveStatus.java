package com.example.kafkacrusher.connection.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ActiveStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    @Getter
    private String value;
}