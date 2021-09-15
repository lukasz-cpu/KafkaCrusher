package com.example.kafkacrusher.client.connection;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@AllArgsConstructor
@RestController
@Slf4j
public class ConnectionActiveManager {


    private ClientConnectionRepository clientConnectionRepository;

    //fixme not working as expected
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

        boolean validateKafkaAddress = false;
        AdminClient adminClient = null;

        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        adminClient = AdminClient.create(props);
        
//                String host = adminClient.describeCluster().controller().get().host();

        return flag;
    }
}
