package com.example.kafkacrusher.connection;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name = "client_connection2")
public class ClientConnection2 {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //FIX ME need to investigate UUID as ID to have better hash code equals
    private Long id;
    @Column(unique = true)
    private String connectionName;
    private String brokers;
    @Column(nullable = false)
    private Boolean isActive;


    


}
