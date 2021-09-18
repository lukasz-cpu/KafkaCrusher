package com.example.kafkacrusher.topic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {



        @GetMapping("/getTopicListForConnectionName")
        public String getTopicForConnectionName(){
            return "asd";
        }

}
