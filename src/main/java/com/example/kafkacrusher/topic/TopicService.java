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
            List<String> result = adminClient.listTopics(listTopicsOptions).names().get()
                    .stream().filter(StringUtils::hasLength).toList();

            if(result.isEmpty()){
                throw new TopicsNameNotFound("Topics name not found for connection name: " + brokerAddresses);
            }
            return result;

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
