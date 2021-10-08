package com.example.kafkacrusher.messages;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponseDTO {
    private String message;
    private String date;
}
