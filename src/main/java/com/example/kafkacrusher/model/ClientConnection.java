package com.example.kafkacrusher.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "client_connection")
public class ClientConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String connectionName;
    private String brokers;
    private String isActive;

    public static final class ClientConnectionBuilder {
        private Long id;
        private String connectionName;
        private String brokers;
        private String isActive;

        private ClientConnectionBuilder() {
        }

        public static ClientConnectionBuilder aClientConnection() {
            return new ClientConnectionBuilder();
        }

        public ClientConnectionBuilder withConnectionName(String connectionName) {
            this.connectionName = connectionName;
            return this;
        }

        public ClientConnectionBuilder withBrokers(String brokers) {
            this.brokers = brokers;
            return this;
        }

        public ClientConnectionBuilder withIsActive(String isActive) {
            this.isActive = isActive;
            return this;
        }

        public ClientConnection build() {
            ClientConnection clientConnection = new ClientConnection();
            clientConnection.connectionName = this.connectionName;
            clientConnection.brokers = this.brokers;
            clientConnection.isActive = this.isActive;
            return clientConnection;
        }
    }
}
