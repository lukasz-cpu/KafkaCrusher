package com.example.kafkacrusher.connection;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    private String brokers;
    @Column(nullable = false)
    private Boolean isActive;

    public static final class ClientConnectionBuilder {
        private Long id;
        private String connectionName;
        private String brokers;
        private Boolean isActive;

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

        public ClientConnectionBuilder withIsActive(Boolean isActive) {
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
