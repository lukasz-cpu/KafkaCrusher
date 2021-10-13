package com.example.kafkacrusher.connection;


import lombok.*;

@Builder
@Data
@NoArgsConstructor
public class ClientConnectionRequestDTO {
    private String connectionName;
    private String brokers;
}
