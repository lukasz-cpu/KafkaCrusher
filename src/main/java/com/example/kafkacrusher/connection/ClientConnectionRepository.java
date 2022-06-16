package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.connection.entity.Address;
import com.example.kafkacrusher.connection.entity.Broker;
import com.example.kafkacrusher.connection.entity.ClientConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface ClientConnectionRepository extends JpaRepository<ClientConnection, Long> {
    List<ClientConnection> findByConnectionName(String name);

    default String getBrokerAddressByConnectionName(String name) {
        return findByConnectionName(name)
                .stream()
                .findFirst()
                .map(ClientConnection::getBroker)
                .map(this::getAddressesFromBroker)
                .get();
    }


    private String getAddressesFromBroker(Broker broker) {

        Set<Address> addresses = broker.getServerAddresses().keySet();


        StringBuilder str
                = new StringBuilder();

        for (Address address : addresses) {
            str.append(address.address);
            str.append(",");
        }
        return str.toString();

    }
}