package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.connection.dto.BrokerDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientConnectionResponseDTO {
    private String connectionName;
    private BrokerDTO brokerDTO;
}
