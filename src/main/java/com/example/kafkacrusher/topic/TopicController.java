package com.example.kafkacrusher.topic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class TopicController {


    private final TopicService topicService;

    @GetMapping("/getTopicListForConnectionName")
    public String getTopicsForConnectionName(@RequestParam String connectionName) throws TopicsNameNotFound {

        List<String> topicsNames = topicService.getTopicsNames(connectionName);

        return topicsNames.toString();


    }

}
