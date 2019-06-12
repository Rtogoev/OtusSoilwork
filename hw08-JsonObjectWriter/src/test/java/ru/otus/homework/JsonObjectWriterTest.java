package ru.otus.homework;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterTest {

    private JsonObjectWriter jsonObjectWriter;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        jsonObjectWriter = new JsonObjectWriter();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void toJsonByte() {
        byte[] expected = "byte".getBytes();
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        byte[].class
                )
        );
    }

    @Test
    void toJsonInt() {
        int[] expected = new int[]{1, 2, 3, 4};
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        int[].class
                )
        );
    }

    @Test
    void toJsonShort() {
        short[] expected = new short[]{1, 2, 3, 4};
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        short[].class
                )
        );
    }

    @Test
    void toJsonLong() {
        long[] expected = new long[]{1, 2, 3, 4};
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        long[].class
                )
        );
    }

    @Test
    void toJsonDouble() {
        double[] expected = new double[]{1.1, 2.2, 3.3, 4.4};
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        double[].class
                )
        );
    }

    @Test
    void toJsonObject() {
        ExperimentalMouse[] expected = new ExperimentalMouse[]{
                ExperimentalMouse.createDefault(),
                ExperimentalMouse.createDefault()
        };
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        ExperimentalMouse[].class
                )
        );
    }

    @Test
    void toJsonCollection() {
        Collection<ExperimentalMouse> expected = new ArrayList<>();
        expected.add(ExperimentalMouse.createDefault());
        expected.add(ExperimentalMouse.createDefault());
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        Collection.class
                )
        );
    }

    @Test
    void toJsonString() {
        String[] expected = new String[]{"expected1", "expected2"};
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        String[].class
                )
        );
    }

    @Test
    void toJsonFloat() {
        float[] expected = new float[]{1.1f, 2.2f, 3.3f, 4.4f};
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        float[].class
                )
        );
    }
}