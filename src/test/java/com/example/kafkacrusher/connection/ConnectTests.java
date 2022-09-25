package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.KafkaCrusherApplication;
import com.example.kafkacrusher.connection.dto.ClientConnectionDTO;
import com.example.kafkacrusher.util.GsonTestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.BasicJsonTester;
import org.springframework.boot.test.json.JsonContent;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class ConnectTests {


    private final RestTemplate restTemplate = new TestRestTemplate().getRestTemplate();

    @Test
    void connect() throws JSONException {
        //given
        ClientConnectionDTO connection_test = ClientConnectionDTO.builder().connectionName("connection test").brokerAddress("localhost:9092").build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(GsonTestUtils.getInstance().getGson().toJson(connection_test), headers);


        //when
        //defined in props
        String url = "http://localhost:8099";
        String response = restTemplate.postForObject(url + "/registerConnection", entity, String.class);


        var expectedResult = "{\n" +
                "  \"connectionName\": \"connection test\",\n" +
                "  \"broker\": {\n" +
                "    \"serverAddresses\": [\n" +
                "      [\n" +
                "        {\n" +
                "          \"address\": \"localhost:9092\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"isActive\": false\n" +
                "        }\n" +
                "      ]\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        JSONAssert.assertEquals(
                expectedResult, response, JSONCompareMode.LENIENT);
    }
}

