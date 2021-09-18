package com.example.kafkacrusher.topic;

import com.example.kafkacrusher.connection.register.ClientConnection;
import com.example.kafkacrusher.connection.register.ClientConnectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@Slf4j
public class TopicService {

    private final ClientConnectionRepository clientConnectionRepository;


    public TopicService(ClientConnectionRepository clientConnectionRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
    }


    public List<String> getTopicsNames(String connectionName) throws TopicsNameNotFound, BrokerNotFoundException {
        String brokerAddressesByName = getBrokerAddressesByName(connectionName);
        List<String> topicByAddresses = getTopicByAddresses(brokerAddressesByName);
        return topicByAddresses;

    }

    private List<String> getTopicByAddresses(String brokerAddresses) throws TopicsNameNotFound {
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddresses);
        try (AdminClient adminClient = AdminClient.create(props)) {
            ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
            listTopicsOptions.timeoutMs(5000);
            Set<String> strings = adminClient.listTopics(listTopicsOptions).names().get();
            List<String> strings1 = strings.stream().filter(StringUtils::hasLength).toList();
            log.info("--------------------");
            log.info("JESTEM TUTAJ getTopicByAddresses {}", strings1);
            log.info("--------------------");

            return strings1;
        } catch (Exception e) {
            throw new TopicsNameNotFound("Topics name not found for connection name: " + brokerAddresses);
        }
    }


    private String getBrokerAddressesByName(String name) throws BrokerNotFoundException {
        Optional<ClientConnection> brokerConnection = clientConnectionRepository.findByConnectionName(name).stream().findFirst();
        if (brokerConnection.isEmpty()) {
            log.debug("JESTEM TUTAJ getBrokerAddressesByName");
            throw new BrokerNotFoundException("Broker not found");
        }
        return brokerConnection.get().getBrokers();
    }


}
