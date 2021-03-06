package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.configuration.GsonUtils;
import com.example.kafkacrusher.connection.dto.ClientConnectionDTO;
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
    public ResponseEntity<String> connect(@RequestBody ClientConnectionDTO inputClientConnectionDTO) {
        ClientConnection clientConnection = ClientConnectionMapper.map(inputClientConnectionDTO);
        Optional<ClientConnection> registerClientConnection = registrationConnectionService.registerClientConnection(clientConnection);
        if (registerClientConnection.isEmpty()) {
            return new ResponseEntity<>("Error during registering connection.", HttpStatus.NOT_ACCEPTABLE);
        } else {
            String jsonResponse = GsonUtils.toJson(registerClientConnection.get());
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/getConnections")
    public ResponseEntity<String> getConnections() {
        List<ClientConnectionResponseDTO> connectionsInfo = registrationConnectionService.getConnectionsInfo();


        String s = GsonUtils.getInstance()
                .getGson()
                .toJson(connectionsInfo);

        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
