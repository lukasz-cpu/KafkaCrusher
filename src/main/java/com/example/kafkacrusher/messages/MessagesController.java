package com.example.kafkacrusher.messages;

import com.example.kafkacrusher.topic.BrokerNotFoundException;
import com.example.kafkacrusher.topic.TopicsNameNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.kafkacrusher.util.JSONUtils.getJson;

@RestController
@Slf4j
@AllArgsConstructor
public class MessagesController {

    private MessageService messageService;

    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody MessageRequestDTO message) {
        try {
            MessageRequestDTO messageRequestDTO = messageService.processMessageForConnection(message);
            return new ResponseEntity<>("Successfully added message: " + getJson(messageRequestDTO), HttpStatus.OK);
        } catch (MessageProcessingException | BrokerNotFoundException | TopicsNameNotFound e) {
            return new ResponseEntity<>("Problem with sending message for connection name: " + getJson(message), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/readMessagesFromTopic")
    public ResponseEntity<String> readMessagesFromTopic(@RequestParam String connectionName, @RequestParam String topicName){
        try {
            List<MessageResponseDTO> messageFromTopic = messageService.readMessageFromTopic(connectionName, topicName);
            return new ResponseEntity<>("Successfully read messages: " + getJson(messageFromTopic), HttpStatus.OK);
        } catch (ReadMessageFromTopicException | BrokerNotFoundException | TopicsNameNotFound e) {
            return new ResponseEntity<>("Problem with reading messages from topic: " + topicName + ", connection name: " + connectionName, HttpStatus.CONFLICT);
        }
    }
}

//https://stackoverflow.com/questions/47702994/how-to-reliably-get-all-kafka-topic-messages-via-kafkaconsumer