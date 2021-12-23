package com.example.kafkacrusher.connection.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "client_connection")
public class ClientConnection {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //FIX ME need to investigate UUID as ID to have better hash code equals
    private Long id;
    @Column(unique = true)
    private String connectionName;
    @Embedded
    @Column
    private Broker broker;


    public static final class ClientConnectionBuilder {
        private String connectionName;
        private Broker broker;

        private ClientConnectionBuilder() {
        }

        public static ClientConnectionBuilder aClientConnection() {
            return new ClientConnectionBuilder();
        }


        public ClientConnectionBuilder withConnectionName(String connectionName) {
            this.connectionName = connectionName;
            return this;
        }

        public ClientConnectionBuilder withBrokers(Broker brokers) {
            this.broker = brokers;
            return this;
        }

        public ClientConnection build() {
            ClientConnection clientConnection = new ClientConnection();
            clientConnection.setConnectionName(connectionName);
            clientConnection.setBroker(broker);
            return clientConnection;
        }
    }
}
