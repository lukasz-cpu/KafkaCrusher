package com.example.kafkacrusher.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    private static GsonUtils sInstance;
    private final Gson mGson;

    private GsonUtils() {
         mGson = new GsonBuilder()
                 .enableComplexMapKeySerialization()
                 .setPrettyPrinting()
                 .create();
    }

    public static GsonUtils getInstance() {
        if (sInstance == null) {
            sInstance = new GsonUtils();
        }
        return sInstance;
    }

    public Gson getGson() {
        return mGson;
    }

    public static <T> String toJson(T object){
        return getInstance().getGson().toJson(object);
    }

}