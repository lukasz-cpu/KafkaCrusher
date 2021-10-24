package com.example.kafkacrusher.connection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
