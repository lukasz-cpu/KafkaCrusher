package com.example.kafkacrusher.connection.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@ToString
public class Broker {

    @ElementCollection(targetClass = ActiveStatus.class)
    @Enumerated(EnumType.STRING)
    @MapKeyClass(Address.class)
    private Map<Address, ActiveStatus> serverAddresses;





}
