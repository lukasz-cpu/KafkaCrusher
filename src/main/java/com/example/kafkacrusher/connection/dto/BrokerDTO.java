package com.example.kafkacrusher.connection.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrokerDTO {
    private Map<AddressDTO, ActiveStatusDTO> serverAddresses;
}
