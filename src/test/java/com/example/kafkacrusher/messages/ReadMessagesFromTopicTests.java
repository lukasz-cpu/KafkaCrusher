package com.example.kafkacrusher.messages;

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
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.AfterEach;
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
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = KafkaCrusherApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"}, topics = {"TestTopic"})
class ReadMessagesFromTopicTests {


    public static KafkaProducer<String, String> kafkaProducer = null;
    private final RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ClientConnectionRepository clientConnectionRepository;

    public static KafkaProducer<String, String> createKafkaProducer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> producer = null;
        try {
            producer = new KafkaProducer<>(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return producer;
    }

    public static void send(String topic, String msg) {
        if (kafkaProducer == null) {
            kafkaProducer = createKafkaProducer();
        }
        kafkaProducer.send(new ProducerRecord<>(topic, msg));
    }


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


        send("TestTopic", "test message on test topic");
    }

    @Test
    void readMessagesFromTopic() throws JsonProcessingException {
        final String baseUrl = "http://localhost:8099/readMessagesFromTopic?connectionName=connection test10&topicName=TestTopic";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);

        List<MessageResponseDTO> connectionResponseDTOS = objectMapper.readValue(response.getBody(), new TypeReference<>() {
        });

        assertEquals("test message on test topic", connectionResponseDTOS.get(0).getMessage());


    }

    @AfterEach
    void tearDown() {
        kafkaProducer.close();
    }
}
