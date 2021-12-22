package com.example.kafkacrusher.connection.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Embeddable
public enum ActiveStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    @Getter
    private String value;
}