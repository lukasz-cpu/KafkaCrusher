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

    public RegistrationConnectionService(ClientConnectionRepository clientConnectionRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
    }

    public Optional<ClientConnection> registerClientConnection(ClientConnection clientConnection) {
        return saveClientConnection(clientConnection)
                .stream()
                .findFirst()
                .or(Optional::empty);
    }

    private Optional<ClientConnection> saveClientConnection(ClientConnection clientConnection) {
        try{
            return Optional.of(clientConnectionRepository.save(clientConnection));
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        return Optional.empty(); //!fixme cannot save when connection is broken
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
