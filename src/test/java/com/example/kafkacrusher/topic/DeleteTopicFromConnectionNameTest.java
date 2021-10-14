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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.example.kafkacrusher.util.JsonTestUtil.getJson;

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
        HttpEntity<String> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                entity,
                String.class);


        System.out.println(response);

    }
}
