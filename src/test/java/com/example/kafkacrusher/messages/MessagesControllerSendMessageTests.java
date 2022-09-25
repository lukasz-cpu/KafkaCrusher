package com.example.kafkacrusher.messages;

import com.example.kafkacrusher.KafkaCrusherApplication;
import com.example.kafkacrusher.connection.ClientConnectionRepository;
import com.example.kafkacrusher.connection.entity.ActiveStatus;
import com.example.kafkacrusher.connection.entity.Address;
import com.example.kafkacrusher.connection.entity.Broker;
import com.example.kafkacrusher.connection.entity.ClientConnection;
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
import java.util.Map;
import java.util.Objects;

import static com.example.kafkacrusher.util.JsonTestUtil.getJson;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = KafkaCrusherApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"}, topics = {"TestTopic1"})
class MessagesControllerSendMessageTests {

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
    void sendMessage() {
        //given
        MessageRequestDTO connection_test = MessageRequestDTO.builder()
                .message("test message")
                .connectionName("connection test10")
                .topic("TestTopic1")
                .build();

        final String baseUrl = "http://localhost:8099/sendMessage";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(getJson(connection_test), headers);

        ResponseEntity<MessageRequestDTO> response = restTemplate.exchange(baseUrl, HttpMethod.POST, entity, MessageRequestDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("TestTopic1", Objects.requireNonNull(response.getBody()).getTopic());
        assertEquals("test message", Objects.requireNonNull(response.getBody()).getMessage());


          }

}