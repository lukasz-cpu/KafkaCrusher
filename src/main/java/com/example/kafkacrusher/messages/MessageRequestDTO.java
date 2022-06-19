package com.example.kafkacrusher.messages;

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
public class MessageRequestDTO {
    private String connectionName;
    private String topic;
    private String message;
}
