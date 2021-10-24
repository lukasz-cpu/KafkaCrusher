package com.example.kafkacrusher.connection;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ClientConnectionRequestDTO {
    private String connectionName;
    private String brokers;
}
