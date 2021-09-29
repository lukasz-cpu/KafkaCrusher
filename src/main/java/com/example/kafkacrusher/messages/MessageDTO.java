package com.example.kafkacrusher.messages;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDTO {
    private String connectionName;
    private String message;
}
