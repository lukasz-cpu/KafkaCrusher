package com.example.kafkacrusher.connection.register;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        Optional<ClientConnection> clientConnectionResult = Optional.empty();
        try {
            String brokers = clientConnection.getBrokers();
            if (connectionActiveManager.validateKafkaAddress(brokers)) {
                clientConnectionResult = Optional.of(clientConnectionRepository.save(clientConnection));
            }
        } catch (Exception e) {
            throw new RegisterClientException("Error with saving client connection to database : " + clientConnection.toString());
        }

        return clientConnectionResult;
    }

    public List<ClientConnectionResponseDTO> getConnectionsInfo() {
        List<ClientConnection> connections = clientConnectionRepository.findAll();
        List<ClientConnectionResponseDTO> responseDTOList = new ArrayList<>();
        for (ClientConnection connection : connections) {
            ClientConnectionResponseDTO build = ClientConnectionResponseDTO.builder()
                    .id(connection.getId())
                    .brokers(connection.getBrokers())
                    .connectionName(connection.getConnectionName())
                    .isActive(connection.getIsActive())
                    .build();
            responseDTOList.add(build);
        }
        return  responseDTOList;
    }
}
