package com.example.kafkacrusher.messages;


import com.example.kafkacrusher.kafka.KafkaConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Slf4j
public class MessageService {

    private final KafkaConnectionManager kafkaConnectionManager;


    public MessageService(KafkaConnectionManager kafkaConnectionManager) {
        this.kafkaConnectionManager = kafkaConnectionManager;
    }

    public MessageDTO processMessageForConnection(MessageDTO message) throws MessageProcessingException {
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

    public void readMessageFromTopic(String topicName) {
//        Properties props = new Properties();
//        props.put("bootstrap.servers", connection.getProps().get("bootstrapServers"));
//        props.put("group.id", getName());
//        props.put("auto.offset.reset", "earliest");
//        props.put("enable.auto.commit", "true");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        log.info("hahaha" + topicName);

    }
}
