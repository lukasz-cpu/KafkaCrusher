package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.configuration.GsonUtils;
import com.example.kafkacrusher.connection.entity.ActiveStatus;
import com.example.kafkacrusher.connection.entity.Address;
import com.example.kafkacrusher.connection.entity.Broker;
import com.example.kafkacrusher.connection.entity.ClientConnection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
@AllArgsConstructor
@RestController
@Slf4j
public class ConnectionActiveManager {


    private ClientConnectionRepository clientConnectionRepository;


    @GetMapping("/connectionManager/setActiveStatuses")
    public void setActiveStatuses() {
        List<ClientConnection> allConnections = clientConnectionRepository.
                findAll();

        for (ClientConnection connection : allConnections) {
            Broker originBroker = connection.getBroker();
            Broker modifiedBroker = setServerStatusesForBroker(originBroker);
            connection.setBroker(modifiedBroker);
        }

        clientConnectionRepository.saveAll(allConnections);

        log.info("Setting active statuses for connections finished.");
    }

    private void saveConnection(ClientConnection connection) {
        ClientConnection clientConnection = clientConnectionRepository.getById(connection.getId());
        Map<Address, ActiveStatus> serverAddresses = clientConnection.getBroker().getServerAddresses();
        for (Map.Entry<Address, ActiveStatus> address : serverAddresses.entrySet()) {
            String selectedAddress = address.getKey().getAddress();
            boolean isActive = checkAddress(selectedAddress);
            address.getValue().setActive(isActive);
        }
        clientConnectionRepository.save(clientConnection);
    }

    private Broker setServerStatusesForBroker(Broker broker) {
        Map<Address, ActiveStatus> serverAddresses = broker.getServerAddresses();
        for (Map.Entry<Address, ActiveStatus> addressActiveStatusEntry : serverAddresses.entrySet()) {
            addressActiveStatusEntry.getValue().setActive(checkAddress(addressActiveStatusEntry.getKey().getAddress()));
        }
        return Broker.builder().serverAddresses(serverAddresses).build();
    }

    public boolean checkAddress(String kafkaAddress) {
        log.info("Checking Kafka Address: {}", kafkaAddress);
        boolean flag = false;
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        try (AdminClient adminClient = AdminClient.create(props)) {
            DescribeClusterOptions dco = new DescribeClusterOptions();
            dco.timeoutMs(500);
            adminClient.describeCluster(dco).clusterId().get();
            flag = true;
        } catch (Exception e) {
            log.error("Error with validation Kafka Address: {}", kafkaAddress);
        }
        return flag;
    }
}

