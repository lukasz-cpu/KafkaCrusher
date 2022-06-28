package com.example.kafkacrusher.messages;


import com.example.kafkacrusher.connection.ClientConnectionRepository;
import com.example.kafkacrusher.kafka.KafkaConnectionManager;
import com.example.kafkacrusher.topic.TopicService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Component
@Slf4j
public class MessageService {

    private final Date date = new Date();
    private final KafkaConnectionManager kafkaConnectionManager;
    private final ClientConnectionRepository clientConnectionRepository;
    private final TopicService topicService;


    public MessageService(KafkaConnectionManager kafkaConnectionManager, ClientConnectionRepository clientConnectionRepository, TopicService topicService) {
        this.kafkaConnectionManager = kafkaConnectionManager;
        this.clientConnectionRepository = clientConnectionRepository;
        this.topicService = topicService;
    }


    public void processMessage(MessageRequestDTO message) {
        if (connectionContainsTopic(message)) {
            sendMessage(message);
        } else {
            log.warn("No topic found: {}", message.getTopic());
            throw new MessageProcessingException("Problem with sending message");
        }
    }

    public List<MessageResponseDTO> readMessageFromTopic(String connectionName, String topicName) {

        String brokerAddressByConnectionName = getBrokerAddressByConnectionName(connectionName);

        Properties properties = prepareProperties(brokerAddressByConnectionName);

        if (connectionContainsTopic(connectionName, topicName)) {

            List<MessageResponseDTO> messagesFromTopic = getMessagesFromTopic(topicName, properties);

            if (!messagesFromTopic.isEmpty()) {
                return messagesFromTopic;
            } else {
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();


    }

    private void sendMessage(MessageRequestDTO message) {
        String topic = message.getTopic();
        String payload = message.getMessage();
        String connectionName = message.getConnectionName();
        getKafkaTemplate(connectionName)
                .ifPresent(template -> template.send(topic, payload));
    }

    private Optional<KafkaTemplate<String, String>> getKafkaTemplate(String connectionName) {
        Optional<KafkaTemplate<String, String>> kafkaTemplate;
        kafkaTemplate = Optional.of(kafkaConnectionManager.getKafkaTemplate(connectionName));

        return kafkaTemplate;
    }


    private List<MessageResponseDTO> getMessagesFromTopic(String topicName, Properties properties) {
        List<MessageResponseDTO> messages = new ArrayList<>();
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Collections.singletonList(topicName));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(100));
            for (ConsumerRecord<String, String> recordMessage : records) {
                String value = recordMessage.value();
                String formattedDate = getFormattedDateFromTimeStamp(recordMessage);
                MessageResponseDTO buildMessage = MessageResponseDTO.builder().message(value).date(formattedDate).build();
                messages.add(buildMessage);
            }
        }
        return messages;
    }

    private String getBrokerAddressByConnectionName(String connectionName) {

        return clientConnectionRepository.getBrokerAddressByConnectionName(connectionName);


    }


    private Properties prepareProperties(String brokerAddressByConnectionName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokerAddressByConnectionName);
        props.put("group.id", "consumer-test-group-spring-boot");
        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }

    private String getFormattedDateFromTimeStamp(ConsumerRecord<String, String> recordMessage) {
        date.setTime(recordMessage.timestamp());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return formatter.format(date);
    }

    private boolean connectionContainsTopic(String connectionName, String topicName) {
        return topicService.getTopicsNames(connectionName).contains(topicName);
    }

    private boolean connectionContainsTopic(MessageRequestDTO message) {
        return topicService.getTopicsNames(message.getConnectionName()).contains(message.getTopic());
    }


}
