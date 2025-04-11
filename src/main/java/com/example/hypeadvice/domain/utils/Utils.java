package com.example.hypeadvice.domain.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String DATE_TIME_FORMAT_2 = "dd/MM/yyyy HH:mm:ss";

    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        return builder.create();
    }

    public static <T> T jsonToObject(Class<T> clazz, String jsonAsString) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        T object = (T) mapper.readValue(jsonAsString, clazz);
        return object;
    }

    private static String format(Date data, SimpleDateFormat dateTimeFormat) {
        if (data == null) {
            return null;
        }
        return dateTimeFormat.format(data);
    }

    public static String formatDateTime2(Date data) {
        return format(data, DATE_TIME_FORMAT_2);
    }

    public static String format(Date data, String pattern) {
        return format(data, new SimpleDateFormat(pattern));
    }
}
