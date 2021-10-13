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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = KafkaCrusherApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
class ClientConnectionControllerTest {

    private final RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();
    private final String url = "http://localhost:8099"; //defined in props

    @Test
    void connect() {

        String requestJson = "";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        String answer = restTemplate.postForObject(url+"/registerConnection", entity, String.class);

        log.info(answer);




    }

    @Test
    void getConnections() {
    }
}