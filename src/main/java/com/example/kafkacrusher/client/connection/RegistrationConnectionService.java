package com.example.kafkacrusher.client.connection;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public ClientConnection registerClientConnection(ClientConnection clientConnection) {
        try {
            String brokers = clientConnection.getBrokers();
            if (connectionActiveManager.validateKafkaAddress(brokers)) {
                return clientConnectionRepository.save(clientConnection);
            }
        } catch (Exception e) {
            throw new RegisterClientException("Error with saving client connection to database : " + clientConnection.toString());
        }

        return clientConnection;
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
