package com.example.kafkacrusher.kafka;

import com.example.kafkacrusher.connection.ClientConnectionRepository;
import com.example.kafkacrusher.messages.MessageDTO;
import com.example.kafkacrusher.topic.BrokerNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class KafkaConnectionManager {

    private final ClientConnectionRepository clientConnectionRepository;
    private Map<ConnectionInfo, KafkaTemplate<String, String>> connectionKafkaTemplateMap = new HashMap<>();


    public KafkaConnectionManager(ClientConnectionRepository clientConnectionRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
    }

    @SneakyThrows
    public KafkaTemplate<String, String> getOrAddKafkaTemplate(String connectionName){
        String addresses = getBrokerAddressesByName(connectionName);
        ConnectionInfo.ConnectionInfoBuilder connectionInfo = ConnectionInfo.builder()
                .connectionName(connectionName)
                .addresses(addresses);

        

    }


    @SneakyThrows
    public void processMessage(MessageDTO message) {


    }

    private String getBrokerAddressesByName(String name) throws BrokerNotFoundException {
        return clientConnectionRepository.
                findByConnectionName(name)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BrokerNotFoundException("Broker not found"))
                .getBrokers();
    }
}
