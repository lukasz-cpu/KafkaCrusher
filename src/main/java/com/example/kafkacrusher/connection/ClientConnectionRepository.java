package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.topic.BrokerNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClientConnectionRepository extends JpaRepository<ClientConnection, Long> {
    List<ClientConnection> findByConnectionName(String name);

    default String getBrokerAddressByConnectionName(String name) throws BrokerNotFoundException {
        return findByConnectionName(name)
                .stream()
                .findFirst()
                .map(ClientConnection::getBrokers)
                .orElse("");
    }
}