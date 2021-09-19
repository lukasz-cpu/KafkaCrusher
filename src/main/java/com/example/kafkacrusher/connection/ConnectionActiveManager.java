package com.example.kafkacrusher.connection;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Properties;

@Component
@AllArgsConstructor
@RestController
@Slf4j
public class ConnectionActiveManager {


    private ClientConnectionRepository clientConnectionRepository;


    @GetMapping("/connectionManager/setActiveStatuses")
    public void setActiveStatuses() {
        final List<ClientConnection> connections = clientConnectionRepository.findAll();
        for (ClientConnection connection : connections) {
            String brokers = connection.getBrokers();
            boolean isActive = validateKafkaAddress(brokers);
            connection.setIsActive(isActive);
            clientConnectionRepository.save(connection);
        }
    }


    public boolean validateKafkaAddress(String kafkaAddress) {
        boolean flag = false;
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        try (AdminClient adminClient = AdminClient.create(props)) {
            DescribeClusterOptions dco = new DescribeClusterOptions();
            dco.timeoutMs(5000);
            adminClient.describeCluster(dco).clusterId().get();
            flag = true;
        } catch (Exception e) {
            log.error("Error with validation Kafka Address: {}", kafkaAddress);
        }
        return flag;
    }
}

