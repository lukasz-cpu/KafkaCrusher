package com.example.kafkacrusher.topic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class TopicListDTO {
    @JsonProperty("topicList")
    List<String> topicListDTO;
}
