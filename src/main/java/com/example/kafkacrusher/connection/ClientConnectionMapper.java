package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.connection.dto.ActiveStatusDTO;
import com.example.kafkacrusher.connection.dto.AddressDTO;
import com.example.kafkacrusher.connection.dto.BrokerDTO;
import com.example.kafkacrusher.connection.dto.ClientConnectionDTO;
import com.example.kafkacrusher.connection.entity.ActiveStatus;
import com.example.kafkacrusher.connection.entity.Address;
import com.example.kafkacrusher.connection.entity.Broker;
import com.example.kafkacrusher.connection.entity.ClientConnection;

import java.util.HashMap;
import java.util.Map;

public class ClientConnectionMapper {

    private ClientConnectionMapper() {

    }

    static ClientConnection map(ClientConnectionDTO clientConnectionRequestDTO) {
        var broker = getBrokerFromClientConnectionRequestDTO(clientConnectionRequestDTO);
        return ClientConnection.ClientConnectionBuilder.aClientConnection()
                .withConnectionName(clientConnectionRequestDTO.getConnectionName())
                .withBrokers(broker)
                .build();
    }


    static ClientConnectionResponseDTO map(ClientConnection clientConnection) {

        BrokerDTO brokerDTO = getBrokerDTOFromClientConnection(clientConnection);

        return ClientConnectionResponseDTO.builder().connectionName(clientConnection.getConnectionName())
                .brokerDTO(brokerDTO)
                .build();

    }

    private static BrokerDTO getBrokerDTOFromClientConnection(ClientConnection clientConnection) {

        Map<Address, ActiveStatus> serverAddresses = clientConnection.getBroker().getServerAddresses();
        Map<AddressDTO, ActiveStatusDTO> mappedResult = new HashMap<>();

        for (var entry : serverAddresses.entrySet()) {
            mappedResult.put(new AddressDTO(entry.getKey().getAddress()), new ActiveStatusDTO(entry.getValue().isActive));
        }


        return BrokerDTO.builder().serverAddresses(mappedResult).build();
    }

    private static Broker getBrokerFromClientConnectionRequestDTO(ClientConnectionDTO clientConnectionRequestDTO) {
        Map<Address, ActiveStatus> result = new HashMap<>();
        result.put(new Address(clientConnectionRequestDTO.getBrokerAddress()), new ActiveStatus(false));
        return Broker.builder().serverAddresses(result).build();
    }


}
