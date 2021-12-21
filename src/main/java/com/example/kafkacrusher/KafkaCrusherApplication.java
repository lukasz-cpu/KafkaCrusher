package com.example.kafkacrusher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaCrusherApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaCrusherApplication.class, args);
    }

}
//ZkUtils.getBrokerInfo( 
//https://github.com/metamx/incubator-storm/blob/f6b843d99c874a7139c7339b79cfc6a995f143d2/external/storm-kafka/src/jvm/storm/kafka/DynamicBrokersReader.java#L35
//zaczac przechowywac jako mapa?? 