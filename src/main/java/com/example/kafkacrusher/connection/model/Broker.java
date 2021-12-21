package com.example.kafkacrusher.connection.model;


import lombok.Data;

import java.util.Map;

@Data
public class Broker {
    private Map<Address, ActiveStatus> serverAddresses;
}
