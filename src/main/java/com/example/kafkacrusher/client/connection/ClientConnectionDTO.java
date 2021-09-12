package com.example.kafkacrusher.client.connection;


import lombok.Data;

@Data
public class ClientConnectionDTO {
    private String connectionName;
    private String brokers;
}
