package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.connection.dto.ActiveStatusDTO;
import com.example.kafkacrusher.connection.dto.AddressDTO;
import com.example.kafkacrusher.connection.dto.BrokerDTO;
import com.example.kafkacrusher.connection.entity.ActiveStatus;
import com.example.kafkacrusher.connection.entity.Address;
import com.example.kafkacrusher.connection.entity.Broker;
import com.example.kafkacrusher.connection.entity.ClientConnection;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.util.HashMap;
import java.util.Map;

public class ClientConnectionMapper {

    private ClientConnectionMapper() {

    }

    static ClientConnection map(ClientConnectionRequestDTO clientConnectionRequestDTO) {

        Broker broker = getBrokerFromClientConnectionRequestDTO(clientConnectionRequestDTO);


        return ClientConnection.ClientConnectionBuilder.aClientConnection()
                .withConnectionName(clientConnectionRequestDTO.getConnectionName())
                .withBrokers(broker)
                .build();

    }



    static ClientConnectionResponseDTO map(ClientConnection clientConnection){

        BrokerDTO brokerDTO = getBrokerDTOFromClientConnection(clientConnection);

        ClientConnectionResponseDTO build = ClientConnectionResponseDTO.builder().connectionName(clientConnection.getConnectionName())
                .brokerDTO(brokerDTO)
                .build();

        return build;

    }

    private static BrokerDTO getBrokerDTOFromClientConnection(ClientConnection clientConnection) {

        Map<Address, ActiveStatus> serverAddresses = clientConnection.getBroker().getServerAddresses();
        Map<AddressDTO, ActiveStatusDTO> mappedResult = new HashMap<>();

        for(var entry : serverAddresses.entrySet()) {
            mappedResult.put(new AddressDTO(entry.getKey().address), new ActiveStatusDTO(entry.getValue().activeStatus));
        }



        return BrokerDTO.builder().serverAddresses(mappedResult).build();
    }

    private static Broker getBrokerFromClientConnectionRequestDTO(ClientConnectionRequestDTO clientConnectionRequestDTO) {
        Map<AddressDTO, ActiveStatusDTO> serverAddresses = clientConnectionRequestDTO.getBrokerDTO().getServerAddresses();
        Map<Address, ActiveStatus> mappedResult = new HashMap<>();


        for (var entry : serverAddresses.entrySet()) {
            mappedResult.put(new Address(entry.getKey().address), new ActiveStatus(entry.getValue().activeStatus));
        }


        return Broker.builder().serverAddresses(mappedResult).build();
    }


}