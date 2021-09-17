package com.example.kafkacrusher.client.connection;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegistrationConnectionService {

    private ClientConnectionRepository clientConnectionRepository;
    private ConnectionActiveManager connectionActiveManager;

    public RegistrationConnectionService(ClientConnectionRepository clientConnectionRepository,
                                         ConnectionActiveManager connectionActiveManager) {
        this.clientConnectionRepository = clientConnectionRepository;
        this.connectionActiveManager = connectionActiveManager;
    }

    public ClientConnection registerClientConnection(ClientConnection clientConnection) {
        String brokers = clientConnection.getBrokers();
        if(connectionActiveManager.validateKafkaAddress(brokers)){
            return clientConnectionRepository.save(clientConnection);
        }
        else{
            throw new RegisterClientException("Error with saving {} to database : " + clientConnection);
        }
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
