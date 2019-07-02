package ru.otus.homework;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class JsonObjectWriter {

    public <T> String toJson(T object) {
        return toJsonValue(object).toString();
    }

    private  <T> JsonValue toJsonValue(T object) {
        if (object == null) {
            return null;
        }

        if (object.getClass().equals(Short.class)) {
            return Json.createValue((short)object);
        }

        if (object.getClass().equals(Byte.class)) {
            return Json.createValue((byte)object);
        }

        if (object.getClass().equals(Integer.class)) {
            return Json.createValue((int)object);
        }


        if (object.getClass().equals(Float.class)) {
            return Json.createValue((float)object);
        }

        if (object.getClass().equals(Double.class)) {
            return Json.createValue((double)object);
        }

        if (object.getClass().equals(Long.class)) {
            return Json.createValue((long)object);
        }

        if (object.getClass().isArray()) {
            return toJsonArray(object);
        }
        if (Collection.class.isAssignableFrom(object.getClass())) {
            return toJsonCollection(object);
        }


        if (object.getClass().equals(String.class)) {
            String string = (String) object;
            return Json.createValue(string);
        }
        return toJsonObject(object);
    }

    private <T> JsonArray toJsonCollection(T object) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        Collection collection = (Collection) object;
        for (Object item : collection) {
            arrayBuilder.add(toJsonValue(item));
        }
        return arrayBuilder.build();
    }

    private <T> JsonArray toJsonArray(T object) {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < Array.getLength(object); i++) {
            Object item = Array.get(object, i);
            arrayBuilder.add(toJsonValue(item));
        }
        return arrayBuilder.build();
    }

    private <T> JsonObject toJsonObject(T object) {
        if (object == null) {
            return null;
        }
        JsonObjectBuilder builder = Json.createObjectBuilder();
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                builder.add(
                        field.getName(),
                        toJsonValue(field.get(object))
                );
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return builder.build();
    }
}
