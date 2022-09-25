package com.example.kafkacrusher.topic;

import com.example.kafkacrusher.KafkaCrusherApplication;
import com.example.kafkacrusher.connection.ClientConnectionRepository;
import com.example.kafkacrusher.connection.entity.ActiveStatus;
import com.example.kafkacrusher.connection.entity.Address;
import com.example.kafkacrusher.connection.entity.Broker;
import com.example.kafkacrusher.connection.entity.ClientConnection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.kafkacrusher.util.JsonTestUtil.getJson;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = KafkaCrusherApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class AddTopicsForConnectionNameTests {

    private final RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ClientConnectionRepository clientConnectionRepository;

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
                .withConnectionName("connection_one13434")
                .withBrokers(result)
                .build();

        clientConnectionRepository.save(connection_one1);

    }

    @Test
    void addTopicsForConnectionName() throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final String baseUrl = "http://localhost:8099/addTopicsForConnectionName?connectionName=connection_one13434";
        TopicListDTO request = TopicListDTO.builder().topicList(Arrays.asList("TestTopic2", "TestTopic3", "TestTopic4", "Topic7")).build();


        HttpEntity<String> entity = new HttpEntity<>(getJson(request), headers);
        HttpEntity<String> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                entity,
                String.class);

        TopicListDTO connectionResponseDTOS = objectMapper.readValue(response.getBody(), new TypeReference<>() {
        });

        List<String> topicListDTO = connectionResponseDTOS.getTopicList();

        assertAll(
                () -> Assertions.assertTrue(topicListDTO.contains("TestTopic2")),
                () -> Assertions.assertTrue(topicListDTO.contains("TestTopic3")),
                () -> Assertions.assertTrue(topicListDTO.contains("TestTopic4")),
                () -> Assertions.assertTrue(topicListDTO.contains("Topic7"))

        );

    }


}