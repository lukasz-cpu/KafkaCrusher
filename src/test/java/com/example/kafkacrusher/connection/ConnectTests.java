package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.KafkaCrusherApplication;
import com.example.kafkacrusher.connection.dto.ClientConnectionDTO;
import com.example.kafkacrusher.util.GsonTestUtils;
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


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = KafkaCrusherApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class ConnectTests {


    private final RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();

    @Test
    void connect() {
        //given
        ClientConnectionDTO connection_test = ClientConnectionDTO.builder().connectionName("connection test").brokerAddress("localhost:9092").build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(GsonTestUtils.getInstance().getGson().toJson(connection_test), headers);


        //when
        //defined in props
        String url = "http://localhost:8099";
        String response = restTemplate.postForObject(url + "/registerConnection", entity, String.class);


        System.out.println(response);


    }
}

