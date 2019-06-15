package ru.otus.homework;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class JsonObjectWriter {

    public <T> String toJson(T object) {
        if (object == null) {
            return null;
        }
        if (object.getClass().getSimpleName().equals("String")) {
            return object.toString();
        }

        if (object.getClass().getSuperclass().getSimpleName().equals("Number")) {
            return object.toString();
        }
        if (object.getClass().isArray()) {
            return toJsonArray(object);
        }
        if (object.getClass().getSuperclass().getSimpleName().equals("Object")) {
            return toJsonObject(object);
        }

        return null;
    }

    private <T> String toJsonArray(T object) {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < Array.getLength(object); i++) {
            Object item = Array.get(object, i);
            arrayBuilder.add(toJson(item));
        }
        return arrayBuilder.build().toString();
    }

    private <T> String toJsonObject(T object) {
        if (object == null) {
            return null;
        }
        JsonObjectBuilder builder = Json.createObjectBuilder();
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                builder.add(field.getName(), toJson(field.get(object)));
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return builder.build().toString();
    }
}
