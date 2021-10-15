package com.example.kafkacrusher.messages;

import com.example.kafkacrusher.KafkaCrusherApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.example.kafkacrusher.util.JsonTestUtil.getJson;
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
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"}, topics = {"TestTopic"})
class MessagesControllerSendMessageTests {

    private final RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();

    @Test
    void sendMessage() {
        //given
        MessageRequestDTO connection_test = MessageRequestDTO.builder()
                .message("test message")
                .connectionName("connection test10")
                .topic("TestTopic")
                .build();

        final String baseUrl = "http://localhost:8099/readMessagesFromTopic";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(getJson(connection_test), headers);

        ResponseEntity<MessageRequestDTO> response = restTemplate.exchange(baseUrl,HttpMethod.POST, entity,MessageRequestDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TestTopic", Objects.requireNonNull(response.getBody()).getTopic());
        assertEquals("test message", Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals("TestTopic", Objects.requireNonNull(response.getBody()).getTopic());


    }
}