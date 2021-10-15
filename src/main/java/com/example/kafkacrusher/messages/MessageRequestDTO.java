package com.example.kafkacrusher.messages;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class MessageRequestDTO {
    private String connectionName;
    private String topic;
    private String message;
}
