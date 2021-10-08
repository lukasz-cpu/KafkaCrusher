package com.example.kafkacrusher.messages;


import com.example.kafkacrusher.connection.ClientConnectionRepository;
import com.example.kafkacrusher.kafka.KafkaConnectionManager;
import com.example.kafkacrusher.topic.BrokerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Component
@Slf4j
public class MessageService {

    private final Date date = new Date();
    private final KafkaConnectionManager kafkaConnectionManager;
    private final ClientConnectionRepository clientConnectionRepository;



    public MessageService(KafkaConnectionManager kafkaConnectionManager, ClientConnectionRepository clientConnectionRepository) {
        this.kafkaConnectionManager = kafkaConnectionManager;
        this.clientConnectionRepository = clientConnectionRepository;
    }

    public MessageRequestDTO processMessageForConnection(MessageRequestDTO message) throws MessageProcessingException {
        String connectionName = message.getConnectionName();
        try {
            KafkaTemplate<String, String> kafkaTemplate = kafkaConnectionManager.getKafkaTemplate(connectionName);
            String topic = message.getTopic();
            String payload = message.getMessage();
            kafkaTemplate.send(topic, payload);  //FIXME set timeout hehe
        } catch (Exception e) {
            throw new MessageProcessingException("Error with sending message to Kafka");
        }
        return message;
    }

    public List<MessageResponseDTO> readMessageFromTopic(String connectionName, String topicName) {
        String brokerAddressByConnectionName = null;

        try {
            brokerAddressByConnectionName = clientConnectionRepository.getBrokerAddressByConnectionName(connectionName);
        } catch (BrokerNotFoundException e) {
            e.printStackTrace();
        }

        List<MessageResponseDTO> messages = new ArrayList<>();

        Properties props = new Properties();
        props.put("bootstrap.servers", brokerAddressByConnectionName);
        props.put("group.id", "consumer-test-group-spring-boot");
        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(100));  //returns immediately if there are records available. Otherwise, it will await (loop for timeous ms for polling)

        for (ConsumerRecord<String, String> recordMessage : records) {
            String value = recordMessage.value();
            String formattedDate = getFormattedDateFromTimeStamp(recordMessage);
            MessageResponseDTO buildMessage = MessageResponseDTO.builder().message(value).date(formattedDate).build();
            messages.add(buildMessage);
        }

        consumer.close();

        return messages;

    }

    private String getFormattedDateFromTimeStamp(ConsumerRecord<String, String> recordMessage) {
        date.setTime(recordMessage.timestamp());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }

}


//https://stackoverflow.com/questions/28561147/how-to-read-data-using-kafka-consumer-api-from-beginning

