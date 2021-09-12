package com.example.kafkacrusher.controller;


import org.springframework.stereotype.Component;

@Component
public class RegistrationConnectionService {

    private ClientConnectionRepository clientConnectionRepository;

    public RegistrationConnectionService(ClientConnectionRepository clientConnectionRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
    }

    public void registerClientConnection(ClientConnection clientConnection) {
        clientConnectionRepository.save(clientConnection);
    }
}
