package com.example.kafkacrusher.model;


import lombok.Data;

@Data
public class ClientConnectionDTO {
    private String connectionName;
    private String brokers;
}
