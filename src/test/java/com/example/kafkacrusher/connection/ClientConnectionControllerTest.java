package com.example.kafkacrusher.connection;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class ClientConnectionControllerTest {

    @Test
    void connect() {
        //given
        ClientConnectionRequestDTO.builder()
                .connectionName("connection test")
                .brokers("192.168.0.74:9092");
        //when
        

    }

    @Test
    void getConnections() {
    }
}