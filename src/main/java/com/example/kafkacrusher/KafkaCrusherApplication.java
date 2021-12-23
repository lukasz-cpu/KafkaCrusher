package com.example.kafkacrusher;


import com.example.kafkacrusher.connection.ClientConnectionRepository;
import com.example.kafkacrusher.connection.entity.ActiveStatus;
import com.example.kafkacrusher.connection.entity.Address;
import com.example.kafkacrusher.connection.entity.Broker;
import com.example.kafkacrusher.connection.entity.ClientConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class KafkaCrusherApplication implements CommandLineRunner {



    @Autowired
    private ClientConnectionRepository clientConnectionRepository;



    public static void main(String[] args) {
        SpringApplication.run(KafkaCrusherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Map<Address, ActiveStatus> resultMap = new HashMap<>();


        Address build = Address.builder()
                .address("localhost:9092")
                .build();

        ActiveStatus trueOne = ActiveStatus.builder()
                .activeStatus("true").build();


        resultMap.put(build, trueOne);

        Broker result = Broker.builder().serverAddresses(resultMap).build();

        ClientConnection connection_one1 = ClientConnection.ClientConnectionBuilder.aClientConnection()
                .withConnectionName("connection one1")
                .withBrokers(result)
                .build();

        clientConnectionRepository.save(connection_one1);


        List<ClientConnection> all = clientConnectionRepository.findAll();

        for (ClientConnection clientConnection : all) {
            System.out.println(clientConnection.toString());
        }


    }
}








//ZkUtils.getBrokerInfo( 
//https://github.com/metamx/incubator-storm/blob/f6b843d99c874a7139c7339b79cfc6a995f143d2/external/storm-kafka/src/jvm/storm/kafka/DynamicBrokersReader.java#L35
// zaczac przechowywac jako mapa??


//connection name1 ->> broker1 --> mapa<adres, active>