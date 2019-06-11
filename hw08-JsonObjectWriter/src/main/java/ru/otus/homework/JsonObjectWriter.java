package ru.otus.homework;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.Collection;

public class JsonObjectWriter {
    private final JsonObjectBuilder builder;

    public JsonObjectWriter() {
        this.builder = Json.createObjectBuilder();
    }


    public String toJson(short[] shortArray) {
        return null;
    }

    public String toJson(byte[] byteArray) {
        return null;
    }

    public String toJson(Object[] objectArray) {
        return null;
    }

    public String toJson(int[] intArray) {
        return null;
    }

    public String toJson(long[] longArray) {
        return null;
    }

    public String toJson(float[] floatArray) {
        return null;
    }

    public String toJson(double[] doubleArray) {
        return null;
    }

    public String toJson(Collection collection) {
        return null;
    }
}
