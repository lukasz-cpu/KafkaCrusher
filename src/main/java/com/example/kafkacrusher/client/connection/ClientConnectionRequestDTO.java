package com.example.kafkacrusher.client.connection;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientConnectionRequestDTO {
    private String connectionName;
    private String brokers;
}
