package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.KafkaCrusherApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static com.example.kafkacrusher.util.JsonTestUtil.getJson;
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
class ClientConnectionControllerTest {


    private final RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();


    @Test
    void connect() {
        //given
        ClientConnectionRequestDTO connection_test = ClientConnectionRequestDTO.builder()
                .connectionName("connection test")
                .brokers("localhost:9092")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(getJson(connection_test), headers);

        //when
        //defined in props
        String url = "http://localhost:8099";
        String response = restTemplate.postForObject(url + "/registerConnection", entity, String.class);

        //then
        assert response != null;
        assertTrue(response.contains("\"connectionName\" : \"connection test\""));
        assertTrue(response.contains("\"brokers\" : \"localhost:9092\""));

    }
}