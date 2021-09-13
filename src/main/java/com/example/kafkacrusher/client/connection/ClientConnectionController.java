package com.example.kafkacrusher.client.connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ClientConnectionController {

    private RegistrationConnectionService registrationConnectionService;

    @PostMapping(value = "/registerConnection")
    public ResponseEntity<String> connect(@RequestBody ClientConnectionRequestDTO clientConnectionRequestDTO) {
        ClientConnection clientConnection = ClientConnectionMapper.map(clientConnectionRequestDTO);
        registrationConnectionService.registerClientConnection(clientConnection);
        return new ResponseEntity<>("Connection added: " + clientConnectionRequestDTO, HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping(value = "/getConnections")
    public ResponseEntity<ClientConnectionResponseDTO> getConnections(){
        List<ClientConnectionResponseDTO> connectionsInfo = registrationConnectionService.getConnectionsInfo();
        String payload = getJson(connectionsInfo);
        return new ResponseEntity(payload, HttpStatus.OK);
    }

    private String getJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
     }
}
