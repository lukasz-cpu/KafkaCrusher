package com.example.kafkacrusher.client.connection;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegistrationConnectionService {

    private ClientConnectionRepository clientConnectionRepository;

    public RegistrationConnectionService(ClientConnectionRepository clientConnectionRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
    }


    public void registerClientConnection(ClientConnection clientConnection) {
        clientConnectionRepository.save(clientConnection);
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
