package com.example.kafkacrusher.connection.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import java.util.Map;

@Data
@Getter
@Setter
@Embeddable
public class Broker {

    @ElementCollection(targetClass = ActiveStatus.class)
    @MapKeyClass(Address.class)
    private Map<Address, ActiveStatus> serverAddresses;
}
