package com.example.kafkacrusher.producer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@AllArgsConstructor
@RestController
public class MessageProducer {



    @GetMapping("test")
    public void createTopic(){
        System.out.println("haha");
    }
}
