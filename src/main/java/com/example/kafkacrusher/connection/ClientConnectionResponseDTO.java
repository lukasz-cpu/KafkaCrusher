package com.example.kafkacrusher.connection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ClientConnectionResponseDTO {
    private Long id;
    private String connectionName;
    private String brokers;
    @JsonProperty("active")
    private boolean isActive;
}
