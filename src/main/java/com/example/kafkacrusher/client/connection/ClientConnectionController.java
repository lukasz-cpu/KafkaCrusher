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
    public ResponseEntity<String> connect(@RequestBody ClientConnectionDTO clientConnectionDTO) {
        ClientConnection clientConnection = ClientConnectionMapper.map(clientConnectionDTO);
        registrationConnectionService.registerClientConnection(clientConnection);
        return new ResponseEntity("Connection added: " + clientConnectionDTO, HttpStatus.OK);
    }

}
