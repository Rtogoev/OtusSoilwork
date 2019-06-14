package ru.otus.homework;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.lang.reflect.Field;
import java.util.Collection;

public class JsonObjectWriter {
    private final ReflectionService reflectionService;
    private final JsonService jsonService;

    public JsonObjectWriter(
            ReflectionService reflectionService,
            JsonService jsonService
    ) {
        this.reflectionService = reflectionService;
        this.jsonService = jsonService;
    }

    public <T> String toJson(T object) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        try {
            if (object.getClass().getSimpleName().equals("String")) {
                return object.toString();
            }
            if (object.getClass().getSuperclass().getSimpleName().equals("Number")) {
                return object.toString();
            }
            if (object.getClass().getSuperclass().getSimpleName().equals("Object")) {

                for (Field field : object.getClass().getDeclaredFields()) {
                    try {
                        builder.add(field.getName(), field.get(object).toString());
                    } catch (IllegalAccessException e) {
                        field.setAccessible(true);
                        builder.add(field.getName(), field.get(object).toString());
                        field.setAccessible(false);
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return builder.build().toString();

    }

    public <T> String toJson(T[] array) {
        return null;
    }

    public String toJson(Collection collection) {
        return null;
    }
}
