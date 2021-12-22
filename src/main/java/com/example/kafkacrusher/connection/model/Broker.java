package com.example.kafkacrusher.connection.model;


import lombok.Data;

import javax.persistence.Embeddable;
import java.util.Map;

@Data
@Embeddable
public class Broker {
    private Map<Address, ActiveStatus> serverAddresses;
}
