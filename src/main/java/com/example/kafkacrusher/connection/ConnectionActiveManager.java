package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.connection.entity.ActiveStatus;
import com.example.kafkacrusher.connection.entity.Address;
import com.example.kafkacrusher.connection.entity.ClientConnection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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




//        clientConnectionRepository
//                .findAll()
//                .stream()
//                .filter(connection -> validateKafkaAddresses(connection.getBrokers()))
//                .forEach(this::saveConnection);

                                                                          //fixme
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

