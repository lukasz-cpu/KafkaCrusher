package com.example.kafkacrusher.connection.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@AllArgsConstructor
@Embeddable
@NoArgsConstructor
@ToString
public enum ActiveStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    @Getter
    private String value;
}