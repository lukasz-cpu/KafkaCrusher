package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.KafkaCrusherApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = KafkaCrusherApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class ConnectionActiveManagerTest {

    @Autowired
    private ConnectionActiveManager connectionActiveManager;

    @Test
    void validateKafkaAddress() {
        assertTrue(connectionActiveManager.validateKafkaAddresses("localhost:9092"));
        assertTrue(connectionActiveManager.validateKafkaAddresses("192.168.0.74:9091,localhost:9092,192.168.0.74:9093"));
        assertTrue(connectionActiveManager.validateKafkaAddresses("192.168.0.74:9091,192.168.0.74:9092,localhost:9092"));
    }
}