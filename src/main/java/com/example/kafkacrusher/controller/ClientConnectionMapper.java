package com.example.kafkacrusher.controller;

import com.example.kafkacrusher.model.ClientConnection;
import com.example.kafkacrusher.model.ClientConnectionDTO;

public class ClientConnectionMapper {


    static ClientConnection map(ClientConnectionDTO clientConnectionDTO){
        return ClientConnection.ClientConnectionBuilder.aClientConnection()
                .withConnectionName(clientConnectionDTO.getConnectionName())
                .withBrokers(clientConnectionDTO.getBrokers())
                .build();

    }

}
