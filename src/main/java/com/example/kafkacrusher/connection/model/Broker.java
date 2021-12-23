package com.example.kafkacrusher.connection.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.MapKeyClass;
import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@ToString
public class Broker {

    @ElementCollection(targetClass = ActiveStatus.class, fetch = FetchType.EAGER)
    @MapKeyClass(Address.class)
    private Map<Address, ActiveStatus> serverAddresses;

}
