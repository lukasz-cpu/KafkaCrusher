package com.example.kafkacrusher.topic;

import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {



        @GetMapping("/getTopicListForConnectionName")
        public String getTopicForConnectionName(){
            return "asd";
        }

}
