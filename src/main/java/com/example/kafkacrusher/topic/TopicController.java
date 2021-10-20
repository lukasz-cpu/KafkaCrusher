package com.example.kafkacrusher.topic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.kafkacrusher.util.JSONUtils.getJson;

@RestController
@Slf4j
@AllArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/getTopicListForConnectionName")
    public ResponseEntity<String> getTopicsForConnectionName(@RequestParam String connectionName) {
        List<String> topicsNames = topicService.getTopicsNames(connectionName);
        return new ResponseEntity<>(getJson(topicsNames), HttpStatus.OK);
    }

    @PostMapping("/addTopicsForConnectionName")
    public ResponseEntity<TopicListDTO> addTopicsForConnectionName(@RequestBody TopicListDTO topicListDTO,
                                                             @RequestParam String connectionName) {
        topicService.createTopicForConnection(connectionName, topicListDTO);
        return new ResponseEntity<>(topicListDTO, HttpStatus.OK);
    }

    @PostMapping("/deleteTopicsForConnectionName")
    public ResponseEntity<TopicListDTO> deleteTopicsForConnectionName(@RequestBody TopicListDTO topicListDTO,
                                                                @RequestParam String connectionName) {
        topicService.deleteTopicsForConnectionName(connectionName, topicListDTO);
        return new ResponseEntity<>(topicListDTO, HttpStatus.OK);
    }
}
