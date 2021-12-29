package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.KafkaCrusherApplication;
import com.example.kafkacrusher.connection.dto.ActiveStatusDTO;
import com.example.kafkacrusher.connection.dto.AddressDTO;
import com.example.kafkacrusher.connection.dto.BrokerDTO;
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
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

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
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class ConnectTests {


    private final RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void connect() throws JsonProcessingException {


        AddressDTO addressDTO = AddressDTO.builder().address("localhost:9092").build();
        ActiveStatusDTO aTrue = ActiveStatusDTO.builder().activeStatus("true").build();

        Map<AddressDTO, ActiveStatusDTO> result = new HashMap<>();
        result.put(addressDTO, aTrue);


        BrokerDTO build = BrokerDTO.builder().serverAddresses(result).build();


        //given
        ClientConnectionRequestDTO connection_test = ClientConnectionRequestDTO.builder()
                .connectionName("connection test")
                .brokerDTO(build)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(getJson(connection_test), headers);

        //when
        //defined in props
        String url = "http://localhost:8099";
        String response = restTemplate.postForObject(url + "/registerConnection", entity, String.class);

        ClientConnectionRequestDTO connectionResponseDTOS = objectMapper.readValue(response, new TypeReference<>() {
        });

        assertEquals("connection test", connectionResponseDTOS.getConnectionName());


        System.out.println(response.toString());

    }
}

