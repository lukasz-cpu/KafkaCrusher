package com.example.kafkacrusher.messages;


import com.example.kafkacrusher.kafka.KafkaConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
        List<String> messages = new ArrayList<>();

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.0.74:9091,192.168.0.74:9092,192.168.0.74:9093");
        props.put("group.id", "japko");
        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("TestTopic"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));  //returns immediately if there are records available. Otherwise, it will await 2 seconds
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());

                // after each message, query the number of messages of the topic
                Set<TopicPartition> partitions = consumer.assignment();
                Map<TopicPartition, Long> offsets = consumer.endOffsets(partitions);
                for (TopicPartition partition : offsets.keySet()) {
                    System.out.printf("partition %s is at %d\n", partition.topic(), offsets.get(partition));
                }
            }
        }
    }
}

//https://stackoverflow.com/questions/28561147/how-to-read-data-using-kafka-consumer-api-from-beginning

