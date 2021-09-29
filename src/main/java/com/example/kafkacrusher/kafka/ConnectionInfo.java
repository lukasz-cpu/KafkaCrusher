package com.example.kafkacrusher.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectionInfo {
    private String connectionName;
    private String addresses;
}
