package com.example.kafkacrusher.connection;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class RegistrationConnectionService {

    private final ClientConnectionRepository clientConnectionRepository;
    private final ConnectionActiveManager connectionActiveManager;

    public RegistrationConnectionService(ClientConnectionRepository clientConnectionRepository,
                                         ConnectionActiveManager connectionActiveManager) {
        this.clientConnectionRepository = clientConnectionRepository;
        this.connectionActiveManager = connectionActiveManager;
    }

    public Optional<ClientConnection> registerClientConnection(ClientConnection clientConnection) {
        return getClientConnection(clientConnection)
                .stream()
                .findFirst()
                .or(Optional::empty);
    }

    private Optional<ClientConnection> getClientConnection(ClientConnection clientConnection) {
        Optional<ClientConnection> result = Optional.empty();
        try {
            String brokers = clientConnection.getBrokers();
            if (connectionActiveManager.validateKafkaAddresses(brokers)) {
                result = Optional.of(clientConnectionRepository.save(clientConnection));
            }
        } catch (Exception e) {
            log.error("Error with saving client connection to database : " + clientConnection.toString());
        }
        return result;
    }

    public List<ClientConnectionResponseDTO> getConnectionsInfo() {
        return clientConnectionRepository.findAll().stream().map(
                clientConnection -> ClientConnectionResponseDTO.builder()
                        .id(clientConnection.getId())
                        .brokers(clientConnection.getBrokers())
                        .connectionName(clientConnection.getConnectionName())
                        .isActive(clientConnection.getIsActive())
                        .build()
        ).toList();
    }
}
