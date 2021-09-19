package com.example.kafkacrusher.topic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class TopicController {


    private final TopicService topicService;

    @GetMapping("/getTopicListForConnectionName")
    public ResponseEntity<String> getTopicsForConnectionName(@RequestParam String connectionName) {

        try {
            return new ResponseEntity<>(topicService.getTopicsNames(connectionName).toString(), HttpStatus.OK);
        } catch (TopicsNameNotFound | BrokerNotFoundException e) {
            return new ResponseEntity<>("Problem with getting topics for connection name: " + connectionName, HttpStatus.CONFLICT);
        }
    }

}
