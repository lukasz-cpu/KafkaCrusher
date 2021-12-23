package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.connection.entity.ClientConnection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@AllArgsConstructor
@Slf4j
public class ClientConnectionController {

    private RegistrationConnectionService registrationConnectionService;

    @PostMapping(value = "/registerConnection")
    public ResponseEntity<ClientConnectionRequestDTO> connect(@RequestBody ClientConnectionRequestDTO clientConnectionRequestDTO) {
        ClientConnection clientConnection = ClientConnectionMapper.map(clientConnectionRequestDTO);

        Optional<ClientConnection> clientConnectionResult = registrationConnectionService.registerClientConnection(clientConnection);
        if (clientConnectionResult.isEmpty()) {
            return new ResponseEntity<>(clientConnectionRequestDTO, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(clientConnectionRequestDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getConnections")
    public ResponseEntity<List<ClientConnectionResponseDTO>> getConnections() {
        List<ClientConnectionResponseDTO> connectionsInfo = registrationConnectionService.getConnectionsInfo();
        return new ResponseEntity<>(connectionsInfo, HttpStatus.OK);
    }
}
