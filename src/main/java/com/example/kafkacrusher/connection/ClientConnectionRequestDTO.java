package com.example.kafkacrusher.connection;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientConnectionRequestDTO {
    private String connectionName;
    private String brokers;
}
