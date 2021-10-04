package com.example.kafkacrusher.messages;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.kafkacrusher.util.JSONUtils.getJson;

@RestController
@Slf4j
@AllArgsConstructor
public class MessagesController {

    private MessageService messageService;

    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO message) {
        try {
            MessageDTO messageDTO = messageService.processMessageForConnection(message);
            return new ResponseEntity<>("Successfully added message: " + getJson(messageDTO), HttpStatus.OK);
        } catch (MessageProcessingException e) {
            return new ResponseEntity<>("Problem with deleting topics for connection name: " + getJson(message), HttpStatus.CONFLICT);
        }
    }
}
