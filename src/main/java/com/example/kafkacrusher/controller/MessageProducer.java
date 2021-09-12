package com.example.kafkacrusher.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.KafkaFuture;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@AllArgsConstructor
@RestController
public class MessageProducer {


    @GetMapping("test")
    public void createTopic() throws ExecutionException, InterruptedException, TimeoutException {

        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.74:9092");
        AdminClient adminClient = AdminClient.create(config);
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        Set<String> strings = listTopicsResult.names().get(3, TimeUnit.SECONDS);
        for (String string : strings) {
            System.out.println(string);
        }


        System.out.println("haha");
    }
}
