package com.example.kafkacrusher.messages;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.kafkacrusher.util.JSONUtils.getJson;

@Component
@Slf4j
public class MessageService {

    public String processMessageForConnection(MessageDTO message) {
        log.info(getJson(message));

        MessageDTO build = MessageDTO.builder().connectionName("connectionName")
                .message("exampleMessage")
                .build();

        log.info(getJson(build));

        return "";
    }
}
