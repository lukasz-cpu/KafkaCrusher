package com.example.kafkacrusher.controller;

public class ClientConnectionMapper {

    private ClientConnectionMapper(){

    }


    static ClientConnection map(ClientConnectionDTO clientConnectionDTO){
        return ClientConnection.ClientConnectionBuilder.aClientConnection()
                .withConnectionName(clientConnectionDTO.getConnectionName())
                .withBrokers(clientConnectionDTO.getBrokers())
                .build();

    }

}
