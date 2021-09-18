package com.example.kafkacrusher.topic;

import com.example.kafkacrusher.connection.register.ClientConnection;
import com.example.kafkacrusher.connection.register.ClientConnectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class TopicService {

    private ClientConnectionRepository clientConnectionRepository;


    public TopicService(ClientConnectionRepository clientConnectionRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
    }


    public String getTopicsNames(String connectionName){

    }


    private String getBrokerAddressesByName(String name) throws BrokerNotFoundException {
        Optional<ClientConnection> brokerConnection = clientConnectionRepository.findByConnectionName(name).stream().findFirst();
        if (brokerConnection.isEmpty()) {
            throw new BrokerNotFoundException("Broker not found");
        }
        return brokerConnection.get().getBrokers();
    }



}
