package com.example.kafkacrusher.client.connection;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ClientConnectionController {

    private RegistrationConnectionService registrationConnectionService;

    @PostMapping(value = "/registerConnection")
    public ResponseEntity<String> connect(@RequestBody ClientConnectionRequestDTO clientConnectionRequestDTO) {
        ClientConnection clientConnection = ClientConnectionMapper.map(clientConnectionRequestDTO);
        registrationConnectionService.registerClientConnection(clientConnection);
        return new ResponseEntity("Connection added: " + clientConnectionRequestDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getConnections")
    public ResponseEntity<ClientConnectionResponseDTO> getConnections(){

    }

}
