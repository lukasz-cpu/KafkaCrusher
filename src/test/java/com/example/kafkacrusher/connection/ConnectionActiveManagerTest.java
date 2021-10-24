package com.example.kafkacrusher.connection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ConnectionActiveManagerTest {

    @Autowired
    private ConnectionActiveManager connectionActiveManager;

    @Test
    void validateKafkaAddress() {
        boolean b = connectionActiveManager.validateKafkaAddresses("192.168.0.74:9093,localhost:9092");
        System.out.println(b);
    }
}