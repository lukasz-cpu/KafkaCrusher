package com.example.kafkacrusher.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTestUtil {
    public static String getJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
