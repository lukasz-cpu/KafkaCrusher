package com.example.kafkacrusher.client.connection;

public class ClientConnectionMapper {

    private ClientConnectionMapper(){

    }


    static ClientConnection map(ClientConnectionRequestDTO clientConnectionRequestDTO){
        return ClientConnection.ClientConnectionBuilder.aClientConnection()
                .withConnectionName(clientConnectionRequestDTO.getConnectionName())
                .withBrokers(clientConnectionRequestDTO.getBrokers())
                .withIsActive(false)
                .build();

    }

}
