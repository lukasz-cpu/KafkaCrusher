package com.example.kafkacrusher.client.connection;

import lombok.Data;

@Data
public class ClientConnectionResponseDTO {
    private Long id;
    private String connectionName;
    private String brokers;
    private boolean isActive;
}
