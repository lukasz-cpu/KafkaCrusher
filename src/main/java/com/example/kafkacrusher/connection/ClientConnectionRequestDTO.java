package com.example.kafkacrusher.connection;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class ClientConnectionRequestDTO {
    private String connectionName;
    private String brokers;
}
