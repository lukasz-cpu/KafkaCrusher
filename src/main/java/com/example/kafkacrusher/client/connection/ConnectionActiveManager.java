package com.example.kafkacrusher.client.connection;

import lombok.AllArgsConstructor;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@AllArgsConstructor
@RestController
public class ConnectionActiveManager {


    private ClientConnectionRepository clientConnectionRepository;


    @GetMapping("/connectionManager/setActiveStatuses")
    public void setActiveStatuses() {

        final List<ClientConnection> connections = clientConnectionRepository.findAll();
        for (ClientConnection connection : connections) {
            String brokers = connection.getBrokers();
            boolean isActive = checkIsActive(brokers);
            connection.setIsActive(isActive);
            clientConnectionRepository.save(connection);
        }
    }

    private boolean checkIsActive(String server) {
        boolean result = true;
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        AdminClient adminClient = AdminClient.create(config);
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        try {
            listTopicsResult.names().get(1500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            result = false;
        } catch (ExecutionException e) {
            result = false;
        } catch (TimeoutException e) {
            result = false;
        }
        adminClient.close(Duration.ofMillis(1500));

        return result;
    }
}
