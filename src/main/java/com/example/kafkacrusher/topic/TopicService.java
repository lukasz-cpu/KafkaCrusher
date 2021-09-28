package com.example.kafkacrusher.topic;

import com.example.kafkacrusher.connection.ClientConnectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TopicService {

    private final ClientConnectionRepository clientConnectionRepository;


    public TopicService(ClientConnectionRepository clientConnectionRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
    }


    public List<String> getTopicsNames(String connectionName) throws TopicsNameNotFound, BrokerNotFoundException {
        String brokerAddressesByName = getBrokerAddressesByName(connectionName);
        return getTopicByAddresses(brokerAddressesByName);

    }

    private List<String> getTopicByAddresses(String brokerAddresses) throws TopicsNameNotFound {
        List<String> result = new ArrayList<>();
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddresses);
        try (AdminClient adminClient = AdminClient.create(props)) {
            ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
            listTopicsOptions.timeoutMs(5000);
            result = adminClient.listTopics(listTopicsOptions).names().get()
                    .stream().filter(StringUtils::hasLength).toList();

        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TopicsNameNotFound("Topics name not found for connection name: " + brokerAddresses);
        }
        if (result.isEmpty()) {
            throw new TopicsNameNotFound("Topics name not found for connection name: " + brokerAddresses);
        }
        return result;

    }

    public void createTopicForConnection(String connectionName, TopicListDTO topicListDTO) throws CreateTopicException {
        AdminClient adminClient = null;
        try {
            String brokerAddressesByName = getBrokerAddressesByName(connectionName);
            Properties props = new Properties();
            props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddressesByName);
            adminClient = AdminClient.create(props);
            List<NewTopic> topicsList = topicListDTO
                    .getTopicListDTO()
                    .stream()
                    .map(topic -> new NewTopic(topic, 1, (short) 1))
                    .toList();

            adminClient.createTopics(topicsList);

        } catch (Exception e) {
            throw new CreateTopicException("Cannot create topic for connection name " + connectionName);
        }
        finally {
            if(adminClient != null){
                adminClient.close();
            }
        }

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
