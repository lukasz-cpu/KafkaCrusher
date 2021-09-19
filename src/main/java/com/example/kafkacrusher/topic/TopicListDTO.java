package com.example.kafkacrusher.topic;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TopicListDTO {
    @JsonProperty("topicList")
    List<String> topicListDTO;
}
