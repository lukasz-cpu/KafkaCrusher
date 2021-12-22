package com.example.kafkacrusher;

import com.example.kafkacrusher.connection.ClientConnection2;
import com.example.kafkacrusher.connection.model.ActiveStatus;
import com.example.kafkacrusher.connection.model.Address;
import com.example.kafkacrusher.connection.model.Broker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class KafkaCrusherApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(KafkaCrusherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        Address address = new Address("1231");
        ActiveStatus active = ActiveStatus.ACTIVE;


        Map<Address, ActiveStatus> resultMap = new HashMap<>();


        Broker broker = new Broker(resultMap);


        ClientConnection2 clientConnection2 = ClientConnection2.ClientConnection2Builder.aClientConnection2()
                .withConnectionName("LAMBO33")
                .withBrokers(broker)
                .build();


    }
}








//ZkUtils.getBrokerInfo( 
//https://github.com/metamx/incubator-storm/blob/f6b843d99c874a7139c7339b79cfc6a995f143d2/external/storm-kafka/src/jvm/storm/kafka/DynamicBrokersReader.java#L35
// zaczac przechowywac jako mapa??


//connection name1 ->> broker1 --> mapa<adres, active>