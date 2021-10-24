package com.example.kafkacrusher.messages;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class MessageResponseDTO {
    private String message;
    private String date;
}
