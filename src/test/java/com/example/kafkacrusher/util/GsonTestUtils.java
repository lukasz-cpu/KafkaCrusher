package com.example.kafkacrusher.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTestUtils {
    private static GsonTestUtils sInstance;
    private final Gson mGson;

    private GsonTestUtils() {
         mGson = new GsonBuilder()
                 .enableComplexMapKeySerialization()
                 .setPrettyPrinting()
                 .create();
    }

    public static GsonTestUtils getInstance() {
        if (sInstance == null) {
            sInstance = new GsonTestUtils();
        }
        return sInstance;
    }

    public Gson getGson() {
        return mGson;
    }
}