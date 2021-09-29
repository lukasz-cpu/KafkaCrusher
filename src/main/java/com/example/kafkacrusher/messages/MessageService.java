package com.example.kafkacrusher.messages;


import com.example.kafkacrusher.kafka.KafkaConnectionManager;
import com.example.kafkacrusher.topic.BrokerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@Slf4j
public class MessageService {

    private final KafkaConnectionManager kafkaConnectionManager;


    public MessageService(KafkaConnectionManager kafkaConnectionManager) {
        this.kafkaConnectionManager = kafkaConnectionManager;
    }

    public String processMessageForConnection(MessageDTO message) {
        String connectionName = message.getConnectionName();
        try {
            KafkaTemplate<String, String> kafkaTemplate = kafkaConnectionManager.getKafkaTemplate(connectionName);
            String topic = message.getTopic();
            String payload = message.getMessage();
            kafkaTemplate.send(topic, payload);
        } catch (BrokerNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
