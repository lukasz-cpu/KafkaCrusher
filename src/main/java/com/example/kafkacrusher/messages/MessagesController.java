package com.example.kafkacrusher.messages;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
@AllArgsConstructor
public class MessagesController {

    private MessageService messageService;

    @PostMapping("/sendMessage")
    public ResponseEntity<MessageRequestDTO> sendMessage(@RequestBody MessageRequestDTO message) {
        messageService.processMessage(message);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @GetMapping("/readMessagesFromTopic")
    public ResponseEntity<List<MessageResponseDTO>> readMessagesFromTopic(@RequestParam String connectionName, @RequestParam String topicName) {
        List<MessageResponseDTO> messageFromTopic = messageService.readMessageFromTopic(connectionName, topicName);
        return new ResponseEntity<>(messageFromTopic, HttpStatus.OK);

    }
}

//https://stackoverflow.com/questions/47702994/how-to-reliably-get-all-kafka-topic-messages-via-kafkaconsumer