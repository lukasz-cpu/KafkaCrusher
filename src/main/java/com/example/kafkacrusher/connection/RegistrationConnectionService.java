package com.example.kafkacrusher.connection;


import com.example.kafkacrusher.connection.entity.ClientConnection;
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
        return saveClientConnection(clientConnection)
                .stream()
                .findFirst()
                .or(Optional::empty);
    }

    private Optional<ClientConnection> saveClientConnection(ClientConnection clientConnection) {
        return Optional.of(clientConnectionRepository.save(clientConnection));  //!fixme cannot save when connection is broken
    }

    public List<ClientConnectionResponseDTO> getConnectionsInfo() {
        List<ClientConnection> all = clientConnectionRepository.findAll();
        List<ClientConnectionResponseDTO> result = new ArrayList<>();
        for (ClientConnection clientConnection : all) {
            ClientConnectionResponseDTO map = ClientConnectionMapper.map(clientConnection);           //!fixme
            result.add(map);
        }
        return result;
    }
}
