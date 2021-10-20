package com.example.kafkacrusher.topic;

import com.example.kafkacrusher.connection.ClientConnection;
import com.example.kafkacrusher.connection.ClientConnectionRepository;
import com.example.kafkacrusher.connection.ConnectionActiveManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class TopicService {

    private final ClientConnectionRepository clientConnectionRepository;
    private final ConnectionActiveManager connectionActiveManager;


    public TopicService(ClientConnectionRepository clientConnectionRepository, ConnectionActiveManager connectionActiveManager) {
        this.clientConnectionRepository = clientConnectionRepository;
        this.connectionActiveManager = connectionActiveManager;
    }


    public List<String> getTopicsNames(String connectionName) {
        return getClientConnectionByName(connectionName)
                .stream()
                .filter(name -> StringUtils.hasLength(name.getBrokers()))
                .map(name -> getTopicByAddresses(name.getBrokers()))
                .flatMap(List::stream)
                .toList();
    }

    public void createTopicForConnection(String connectionName, TopicListDTO topicListDTO) {
        String brokerAddresses = getBrokerAddresses(connectionName);
        boolean isActive = connectionActiveManager.validateKafkaAddress(brokerAddresses);
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddresses);

        try (AdminClient adminClient = AdminClient.create(props)) {
            if (isActive) {
                List<NewTopic> topicsList = topicListDTO
                        .getTopicListDTO()
                        .stream()
                        .map(topic -> new NewTopic(topic, 1, (short) 1))
                        .toList();

                adminClient.createTopics(topicsList);
            }

        }
    }


    public void deleteTopicsForConnectionName(String connectionName, TopicListDTO topicListDTO) {
        String brokerAddressesByName = getBrokerAddresses(connectionName);
        boolean isActive = connectionActiveManager.validateKafkaAddress(brokerAddressesByName);
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddressesByName);

        if (isActive) {
            deleteTopic(topicListDTO, props);
        }
    }

    private void deleteTopic(TopicListDTO topicListDTO, Properties props) {
        try (AdminClient adminClient = AdminClient.create(props)) {
            List<String> topicsList = topicListDTO
                    .getTopicListDTO()
                    .stream()
                    .toList();
            adminClient.deleteTopics(topicsList);
        }
    }


    private Optional<ClientConnection> getClientConnectionByName(String name) {
        return Optional.of(clientConnectionRepository.
                        findByConnectionName(name)
                        .stream()
                        .findAny())
                .orElse(Optional.empty());


    }

    private String getBrokerAddresses(String connectionName) {
        return getClientConnectionByName(connectionName)
                .stream()
                .map(ClientConnection::getBrokers)
                .filter(StringUtils::hasLength)
                .findFirst().orElse("");
    }

    private Set<String> getTopicList(AdminClient adminClient, ListTopicsOptions listTopicsOptions) {
        Set<String> result = new HashSet<>();
        try {
            result = adminClient
                    .listTopics(listTopicsOptions)
                    .names()
                    .get();
        } catch (InterruptedException e) {
            log.error("InterruptedException: ", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error("ExecutionException: ", e);
        }

        return result;
    }

    private List<String> getTopicByAddresses(String brokerAddresses) {
        List<String> result;
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddresses);

        try (AdminClient adminClient = AdminClient.create(props)) {
            ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
            listTopicsOptions.timeoutMs(5000);
            result = getTopicList(adminClient, listTopicsOptions)
                    .stream()
                    .filter(StringUtils::hasLength)
                    .sorted()
                    .toList();

        }
        return result;

    }
}
