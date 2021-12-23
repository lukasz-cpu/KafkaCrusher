//package com.example.kafkacrusher.connection;
//
//import com.example.kafkacrusher.connection.model.Broker;
//import lombok.Data;
//import lombok.ToString;
//
//import javax.persistence.Column;
//import javax.persistence.Embedded;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//
//
//@Entity(name = "client_connection2")
//@Data
//@ToString
//public class ClientConnection2 {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    //FIX ME need to investigate UUID as ID to have better hash code equals
//    private Long id;
//    @Column(unique = true)
//    private String connectionName;
//    @Embedded
//    @Column
//    private Broker brokers;
//
//
//    public static final class ClientConnection2Builder {
//        private String connectionName;
//        private Broker brokers;
//
//        private ClientConnection2Builder() {
//        }
//
//        public static ClientConnection2Builder aClientConnection2() {
//            return new ClientConnection2Builder();
//        }
//
//
//        public ClientConnection2Builder withConnectionName(String connectionName) {
//            this.connectionName = connectionName;
//            return this;
//        }
//
//        public ClientConnection2Builder withBrokers(Broker brokers) {
//            this.brokers = brokers;
//            return this;
//        }
//
//        public ClientConnection2 build() {
//            ClientConnection2 clientConnection2 = new ClientConnection2();
//            clientConnection2.setConnectionName(connectionName);
//            clientConnection2.setBrokers(brokers);
//            return clientConnection2;
//        }
//    }
//}