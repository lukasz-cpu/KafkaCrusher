package com.example.kafkacrusher.connection.register;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientConnectionRequestDTO {
    private String connectionName;
    private String brokers;
}
