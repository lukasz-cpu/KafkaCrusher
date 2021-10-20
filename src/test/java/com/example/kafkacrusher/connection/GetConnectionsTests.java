package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.KafkaCrusherApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = KafkaCrusherApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class GetConnectionsTests {


    private final RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void getConnections() throws JsonProcessingException {
        //given
        //noting

        //when
        //defined in props
        String url = "http://localhost:8099";
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "/getConnections", String.class);

        //then
        List<ClientConnectionResponseDTO> connectionResponseDTOS = objectMapper.readValue(forEntity.getBody(), new TypeReference<>() {
        });
        assertEquals(10, connectionResponseDTOS.size());

    }
}
