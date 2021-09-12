package com.example.kafkacrusher.client.connection;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientConnectionResponseDTO {
    private Long id;
    private String connectionName;
    private String brokers;
    private boolean isActive;
}
