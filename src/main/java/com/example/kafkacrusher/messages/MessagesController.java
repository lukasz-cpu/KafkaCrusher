package com.example.kafkacrusher.messages;

import com.example.kafkacrusher.topic.BrokerNotFoundException;
import com.example.kafkacrusher.topic.TopicsNameNotFound;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
@AllArgsConstructor
public class MessagesController {

    private MessageService messageService;

    @PostMapping("/sendMessage")
    public ResponseEntity<MessageRequestDTO> sendMessage(@RequestBody MessageRequestDTO message) {
        try {
            MessageRequestDTO messageRequestDTO = messageService.processMessage(message);
            return new ResponseEntity<>(messageRequestDTO, HttpStatus.OK);
        } catch (MessageProcessingException | BrokerNotFoundException | TopicsNameNotFound e) {
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/readMessagesFromTopic")
    public ResponseEntity<List<MessageResponseDTO>> readMessagesFromTopic(@RequestParam String connectionName, @RequestParam String topicName){
        try {
            List<MessageResponseDTO> messageFromTopic = messageService.readMessageFromTopic(connectionName, topicName);
            return new ResponseEntity<>(messageFromTopic, HttpStatus.OK);
        } catch (ReadMessageFromTopicException | TopicsNameNotFound e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.CONFLICT);
        }
    }
}

//https://stackoverflow.com/questions/47702994/how-to-reliably-get-all-kafka-topic-messages-via-kafkaconsumer