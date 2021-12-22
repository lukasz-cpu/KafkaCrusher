package com.example.kafkacrusher.connection.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Address {
    public String address;
}
