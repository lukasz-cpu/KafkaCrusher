package com.example.kafkacrusher.messages;


import com.example.kafkacrusher.connection.ClientConnectionRepository;
import com.example.kafkacrusher.kafka.KafkaConnectionManager;
import com.example.kafkacrusher.topic.BrokerNotFoundException;
import com.example.kafkacrusher.topic.TopicService;
import com.example.kafkacrusher.topic.TopicsNameNotFound;
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
    private final TopicService topicService;


    public MessageService(KafkaConnectionManager kafkaConnectionManager, ClientConnectionRepository clientConnectionRepository, TopicService topicService) {
        this.kafkaConnectionManager = kafkaConnectionManager;
        this.clientConnectionRepository = clientConnectionRepository;
        this.topicService = topicService;
    }


    public MessageRequestDTO processMessage(MessageRequestDTO message) throws MessageProcessingException, BrokerNotFoundException {
        if(connectionContainsTopic(message)){
            sendMessage(message);
        }
        else{
         log.warn("No topic found: {}", message.getTopic());
         throw new MessageProcessingException("Problem with sending message");
        }
        return message;
    }

    private void sendMessage(MessageRequestDTO message){
        String topic = message.getTopic();
        String payload = message.getMessage();
        String connectionName = message.getConnectionName();
        getKafkaTemplate(connectionName)
                .ifPresent(template -> template.send(topic, payload));
    }

    private Optional<KafkaTemplate<String, String>> getKafkaTemplate(String connectionName) {
        Optional<KafkaTemplate<String, String>> kafkaTemplate = Optional.empty();
        try {
            kafkaTemplate = Optional.of(kafkaConnectionManager.getKafkaTemplate(connectionName));
        } catch (BrokerNotFoundException e) {
            e.printStackTrace();
        }
        return kafkaTemplate;
    }

    private boolean connectionContainsTopic(MessageRequestDTO message) throws TopicsNameNotFound {
        return topicService.getTopicsNames(message.getConnectionName()).contains(message.getTopic());
    }


    private boolean connectionContainsTopic(String connectionName, String topicName) throws TopicsNameNotFound {
        return topicService.getTopicsNames(connectionName).contains(topicName);
    }

    public List<MessageResponseDTO> readMessageFromTopic(String connectionName, String topicName) throws ReadMessageFromTopicException, TopicsNameNotFound {

        Optional<String> brokerAddressByConnectionName = getBrokerAddressByConnectionName(connectionName);

        Optional<Properties> properties = brokerAddressByConnectionName.stream().map(this::prepareProperties).findFirst();

        if(connectionContainsTopic(connectionName, topicName)){
            List<MessageResponseDTO> messageResponseDTOS =
                    properties
                            .stream()
                            .map(x -> getMessagesFromTopic(topicName, x))
                            .takeWhile(list -> !list.isEmpty())
                            .findAny()
                            .orElse(new ArrayList<>());



            if(!messageResponseDTOS.isEmpty()){
                return messageResponseDTOS;
            }

            else{
                throw new ReadMessageFromTopicException();
            }
        }
        throw new ReadMessageFromTopicException();

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

    private Optional<String> getBrokerAddressByConnectionName(String connectionName) {
        try {
            return Optional.of(clientConnectionRepository.getBrokerAddressByConnectionName(connectionName));
        } catch (BrokerNotFoundException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }
}


//https://stackoverflow.com/questions/28561147/how-to-read-data-using-kafka-consumer-api-from-beginning

