package com.example.kafkacrusher.model;


import lombok.Data;

import java.util.List;

@Data
public class ClientConnectionDTO {
    private String connectionName;
    private List<String> brokers;
}
