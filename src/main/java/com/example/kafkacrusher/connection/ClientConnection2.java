package com.example.kafkacrusher.connection;

import com.example.kafkacrusher.connection.model.Broker;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;


@Entity(name = "client_connection2")
@Data
public class ClientConnection2 {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //FIX ME need to investigate UUID as ID to have better hash code equals
    private Long id;
    @Column(unique = true)
    private String connectionName;
    @Embedded
    @Column
    private Broker brokers;


}