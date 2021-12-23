package com.example.kafkacrusher.kafka;

import com.example.kafkacrusher.connection.entity.ClientConnection;
import com.example.kafkacrusher.connection.ClientConnectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class KafkaConnectionManager {

    private final ClientConnectionRepository clientConnectionRepository;
    private final Map<ConnectionInfo, KafkaTemplate<String, String>> connectionKafkaTemplateMap = new HashMap<>();


    public KafkaConnectionManager(ClientConnectionRepository clientConnectionRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
    }


    public KafkaTemplate<String, String> getKafkaTemplate(String connectionName) {
        String addresses = getBrokerAddressesByName(connectionName);
        ConnectionInfo connectionInfo = ConnectionInfo.builder()
                .connectionName(connectionName)
                .addresses(addresses)
                .build();

        return connectionKafkaTemplateMap.computeIfAbsent(connectionInfo, value -> createKafkaTemplate(addresses));
    }

    private KafkaTemplate<String, String> createKafkaTemplate(String addresses) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, addresses);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        DefaultKafkaProducerFactory<String, String> stringStringDefaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(props);
        return new KafkaTemplate<>(stringStringDefaultKafkaProducerFactory);

    }

    private String getBrokerAddressesByName(String name) {
        return clientConnectionRepository.
                findByConnectionName(name)
                .stream()
                .filter(clientConnection -> StringUtils.hasLength(clientConnection.getBrokers()))
                .findFirst()
                .map(ClientConnection::getBrokers)
                .orElse("");

    }
}