package com.example.kafkacrusher.topic;

import com.example.kafkacrusher.KafkaCrusherApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Arrays;
import java.util.List;

import static com.example.kafkacrusher.util.JsonTestUtil.getJson;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = KafkaCrusherApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"}, topics = {"TestTopic123555"})
class DeleteTopicFromConnectionNameTest {

    private final RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deleteTopicsForConnectionName() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final String baseUrl = "http://localhost:8099/deleteTopicsForConnectionName?connectionName=connection test10";
        TopicListDTO request = TopicListDTO.builder().topicListDTO(List.of("TestTopic123555")).build();


        HttpEntity<String> entity = new HttpEntity<>(getJson(request), headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl,HttpMethod.POST, entity,String.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
//https://stackoverflow.com/questions/48114040/exception-during-topic-deletion-when-kafka-is-hosted-in-docker-in-windows
//java.nio.file.AccessDeniedException: C:\Users\UKASZ~1\AppData\Local\Temp\spring.kafka.71768bc3-197f-45e0-a250-0052e28afda36175876506604861747\TestTopic123555-0 -> C:\Users\UKASZ~1\AppData\Local\Temp\spring.kafka.71768bc3-197f-45e0-a250-0052e28afda36175876506604861747\TestTopic123555-0.5764ec0bd35f4c71ac0af39938626271-delete
//run tests and project inside docker container???