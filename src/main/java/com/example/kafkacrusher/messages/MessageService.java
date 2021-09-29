package com.example.kafkacrusher.messages;


import com.example.kafkacrusher.connection.ClientConnectionRepository;
import com.example.kafkacrusher.topic.BrokerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageService {

    private final ClientConnectionRepository clientConnectionRepository;

    public MessageService(ClientConnectionRepository clientConnectionRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
    }

    public String processMessageForConnection(MessageDTO message) {

        String connectionName = message.getConnectionName();
        try {
            String brokerAddressesByName = getBrokerAddressesByName(connectionName);
        } catch (BrokerNotFoundException e) {
            e.printStackTrace();
        }


        return "";
    }






    private String getBrokerAddressesByName(String name) throws BrokerNotFoundException {
        return clientConnectionRepository.
                findByConnectionName(name)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BrokerNotFoundException("Broker not found"))
                .getBrokers();
    }
}
