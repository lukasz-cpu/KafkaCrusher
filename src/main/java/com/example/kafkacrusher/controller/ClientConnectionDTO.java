package com.example.kafkacrusher.controller;


import lombok.Data;

@Data
public class ClientConnectionDTO {
    private String connectionName;
    private String brokers;
}
