package com.example.kafkacrusher.topic;

import com.example.kafkacrusher.connection.register.ClientConnection;
import com.example.kafkacrusher.connection.register.ClientConnectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TopicService {

    private final ClientConnectionRepository clientConnectionRepository;


    public TopicService(ClientConnectionRepository clientConnectionRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
    }


    public List<String> getTopicsNames(String connectionName) throws TopicsNameNotFound {
        String brokerAddressesByName;
        try {
            brokerAddressesByName = getBrokerAddressesByName(connectionName);
            return getTopicByAddresses(brokerAddressesByName);
        } catch (BrokerNotFoundException e) {
            throw new TopicsNameNotFound("Broker not found");
        }
    }

    private List<String> getTopicByAddresses(String brokerAddresses) {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddresses);
        try (AdminClient adminClient = AdminClient.create(props)) {
            Set<String> strings = adminClient.listTopics().names().get(5000, TimeUnit.SECONDS);
            return strings.stream().toList();
        } catch (Exception e) {
            log.error("Error with getting topic list for broker address: {}", brokerAddresses);
        }
        return new ArrayList<>();

    }


    private String getBrokerAddressesByName(String name) throws BrokerNotFoundException {
        Optional<ClientConnection> brokerConnection = clientConnectionRepository.findByConnectionName(name).stream().findFirst();
        if (brokerConnection.isEmpty()) {
            throw new BrokerNotFoundException("Broker not found");
        }
        return brokerConnection.get().getBrokers();
    }


}
