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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"}, topics = {"TestTopic123555"})
class DeleteTopicFromConnectionNameTest {

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
                .withConnectionName("connection test10")
                .withBrokers(result)
                .build();

        clientConnectionRepository.save(connection_one1);

    }


    @Test
    void deleteTopicsForConnectionName() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final String baseUrl = "http://localhost:8099/deleteTopicsForConnectionName?connectionName=connection test10";
        TopicListDTO request = TopicListDTO.builder().topicList(List.of("TestTopic123555")).build();


        HttpEntity<String> entity = new HttpEntity<>(getJson(request), headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);

        TopicListDTO connectionResponseDTOS = objectMapper.readValue(response.getBody(), new TypeReference<>() {
        });


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(connectionResponseDTOS.getTopicList().contains("TestTopic123555"));

    }
}
//java.nio.file.AccessDeniedException: C:\Users\UKASZ~1\AppData\Local\Temp\spring.kafka.71768bc3-197f-45e0-a250-0052e28afda36175876506604861747\TestTopic123555-0 -> C:\Users\UKASZ~1\AppData\Local\Temp\spring.kafka.71768bc3-197f-45e0-a250-0052e28afda36175876506604861747\TestTopic123555-0.5764ec0bd35f4c71ac0af39938626271-delete
//https://stackoverflow.com/questions/48114040/exception-during-topic-deletion-when-kafka-is-hosted-in-docker-in-windows
