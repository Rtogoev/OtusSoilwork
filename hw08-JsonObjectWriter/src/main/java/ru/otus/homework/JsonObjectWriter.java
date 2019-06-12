package ru.otus.homework;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.Collection;

public class JsonObjectWriter {

    public <T> String toJson(T object) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        if (object.getClass().getSuperclass().getSimpleName().equals("Number")) {

        }
        if (object.getClass().getSuperclass().getSimpleName().equals("Object")) {

        }

        builder.add(object.toString(), object.toString());

//      companyBuilder.add("websites", websitesBuilder);
//      companyBuilder.add("address", addressBuilder);
//
//    // Root JsonObject
        JsonObject rootJSONObject = builder.build();
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
