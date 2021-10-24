package com.example.kafkacrusher.topic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/getTopicListForConnectionName")
    public ResponseEntity<List<String>> getTopicsForConnectionName(@RequestParam String connectionName) {
        List<String> topicsNames = topicService.getTopicsNames(connectionName);
        return new ResponseEntity<>(topicsNames, HttpStatus.OK);
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
