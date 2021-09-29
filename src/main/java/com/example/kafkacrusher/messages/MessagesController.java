package com.example.kafkacrusher.messages;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
public class MessagesController {

    private MessageService messageService;


    @PostMapping("/sendMessage")
    public String sendMessage(@RequestBody MessageDTO message) {
        return messageService.processMessageForConnection(message);
    }
}
