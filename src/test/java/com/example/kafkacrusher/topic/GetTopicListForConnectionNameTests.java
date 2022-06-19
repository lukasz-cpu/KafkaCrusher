package com.example.kafkacrusher.topic;

import com.example.kafkacrusher.KafkaCrusherApplication;
import com.example.kafkacrusher.connection.ClientConnectionRepository;
import com.example.kafkacrusher.connection.entity.ActiveStatus;
import com.example.kafkacrusher.connection.entity.Address;
import com.example.kafkacrusher.connection.entity.Broker;
import com.example.kafkacrusher.connection.entity.ClientConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = KafkaCrusherApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"}, topics = {"TestTopic33", "TestTopic3321"})
class GetTopicListForConnectionNameTests {


    private final RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();

    @Autowired
    ClientConnectionRepository clientConnectionRepository;

    @BeforeEach
    void setUp() {

        clientConnectionRepository.deleteAll();

        Map<Address, ActiveStatus> resultMap = new HashMap<>();


        Address build = Address.builder()
                .address("localhost:9092")
                .build();

        ActiveStatus trueOne = ActiveStatus
                .builder()
                .isActive(true)
                .build();


        resultMap.put(build, trueOne);

        Broker result = Broker.builder().serverAddresses(resultMap).build();

        ClientConnection connection_one1 = ClientConnection.ClientConnectionBuilder.aClientConnection()
                .withConnectionName("connection test10")
                .withBrokers(result)
                .build();

        clientConnectionRepository.save(connection_one1);

    }


    @Test
    void getTopicsForConnectionName() {
        final String baseUrl = "http://localhost:8099/getTopicListForConnectionName?connectionName=connection test10";

        ResponseEntity<String> response = restTemplate.getForEntity(
                baseUrl,
                String.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).contains("TestTopic33"));
        assertTrue(response.getBody().contains("TestTopic3321"));


    }


}
