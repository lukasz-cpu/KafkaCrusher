package com.example.kafkacrusher.controller;

import com.example.kafkacrusher.model.ClientConnectionDTO;
import com.example.kafkacrusher.service.RegistrationConnectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientConnectionController {

    private RegistrationConnectionService registrationConnectionService;

    @RequestMapping(value = "/registerConnection", method = RequestMethod.POST)
    public ResponseEntity<String> connect(@RequestBody ClientConnectionDTO clientConnectionDTO) {
        ClientConnectionMapper.map(clientConnectionDTO);
        return new ResponseEntity("{}", HttpStatus.OK);
    }

}
