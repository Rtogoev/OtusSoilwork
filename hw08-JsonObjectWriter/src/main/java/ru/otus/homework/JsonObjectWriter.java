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

        if (object.getClass().getSimpleName().equals("String")) {
            return object.toString();
        }
        if (object.getClass().getSuperclass().getSimpleName().equals("Number")) {
            return object.toString();
        }
        if (object.getClass().getSuperclass().getSimpleName().equals("Object")) {
            for (Field declaredField : object.getClass().getDeclaredFields()) {
                builder.add(declaredField.getName(), declaredField.toString());
            }
            return builder.build().toString();
        }

//      companyBuilder.add("websites", websitesBuilder);
//      companyBuilder.add("address", addressBuilder);
//
//    // Root JsonObject
//
//      System.out.println("Root JsonObject: " + rootJSONObject);
//
//    // Write to file
//    File outFile= new File("C:/test/company2.txt");
//      outFile.getParentFile().mkdirs();
//
//    OutputStream os = new FileOutputStream(outFile);
//    JsonWriter jsonWriter = Json.createWriter(os);
//
//      jsonWriter.writeObject(rootJSONObject);
//      jsonWriter.close();
        return null;
    }

    public <T> String toJson(T[] array) {
        return null;
    }

    public String toJson(Collection collection) {
        return null;
    }
}
