package com.example.kafkacrusher.topic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.kafkacrusher.util.JSONUtils.getJson;

@RestController
@Slf4j
@AllArgsConstructor
public class TopicController {


    private final TopicService topicService;

    @GetMapping("/getTopicListForConnectionName")
    public ResponseEntity<String> getTopicsForConnectionName(@RequestParam String connectionName) {

        try {
            List<String> topicsNames = topicService.getTopicsNames(connectionName);
            return new ResponseEntity<>(getJson(topicsNames), HttpStatus.OK);
        } catch (TopicsNameNotFound | BrokerNotFoundException e) {
            return new ResponseEntity<>("Problem with getting topics for connection name: " + connectionName, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/addTopicsForConnectionName")
    public ResponseEntity<String> addTopicsForConnectionsName(@RequestBody TopicListDTO topicListDTO,
                                                              @RequestParam String connectionName){


        try {
            try {
                topicService.createTopicForConnection(connectionName, topicListDTO);
            } catch (CreateTopicException e) {
                e.printStackTrace();
            }
        } catch (BrokerNotFoundException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
