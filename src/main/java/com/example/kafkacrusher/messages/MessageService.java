package com.example.kafkacrusher.messages;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageService {

    public String processMessageForConnection(String message) {
        log.info("message");
        return "";
    }
}
