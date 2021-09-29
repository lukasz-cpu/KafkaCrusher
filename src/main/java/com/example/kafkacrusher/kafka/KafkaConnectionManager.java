package com.example.kafkacrusher.kafka;

import com.example.kafkacrusher.connection.ClientConnectionRepository;
import com.example.kafkacrusher.messages.MessageDTO;
import com.example.kafkacrusher.topic.BrokerNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
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


    public KafkaTemplate<String, String> getOrAddKafkaTemplate(String connectionName) throws BrokerNotFoundException {
        String addresses = getBrokerAddressesByName(connectionName);
        ConnectionInfo connectionInfo = ConnectionInfo.builder()
                .connectionName(connectionName)
                .addresses(addresses)
                .build();

        if(!connectionKafkaTemplateMap.containsKey(connectionInfo)){
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, addresses);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            DefaultKafkaProducerFactory<String, String> stringStringDefaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(props);
            KafkaTemplate<String, String> value = new KafkaTemplate<>(stringStringDefaultKafkaProducerFactory);
            connectionKafkaTemplateMap.put(connectionInfo, value);
        }

        return connectionKafkaTemplateMap.get(connectionInfo);

    }


    @SneakyThrows
    public void processMessage(MessageDTO message) {
log.info(message.toString());

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
