package ru.otus.homework;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectWriterTest {

    private JsonObjectWriter jsonObjectWriter;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        jsonObjectWriter = new JsonObjectWriter();
    }

    @Test
    void toJsonArrayByte() {
        byte[] expected = "byte".getBytes();
        byte[] actual = gson.fromJson(
                jsonObjectWriter.toJson(expected),
                byte[].class
        );
        assertEquals(0, Arrays.compare(expected, actual));
    }

    @Test
    void toJsonArrayInt() {
        int[] expected = new int[]{1, 2, 3, 4};
        int[] actual = gson.fromJson(
                jsonObjectWriter.toJson(expected),
                int[].class
        );
        assertEquals(0, Arrays.compare(expected, actual));
    }

    @Test
    void toJsonArrayShort() {
        short[] expected = new short[]{1, 2, 3, 4};
        short[] actual = gson.fromJson(
                jsonObjectWriter.toJson(expected),
                short[].class
        );
        assertEquals(0, Arrays.compare(expected, actual));
    }

    @Test
    void toJsonArrayString() {
        String[] expected = new String[]{"one", "two", "three"};
        String[] actual = gson.fromJson(
                jsonObjectWriter.toJson(expected),
                String[].class
        );
        assertArrayEquals(expected, actual);
    }

    @Test
    void toJsonArrayLong() {
        long[] expected = new long[]{1, 2, 3, 4};
        long[] actual = gson.fromJson(
                jsonObjectWriter.toJson(expected),
                long[].class
        );
        assertEquals(0, Arrays.compare(expected, actual));
    }

    @Test
    void toJsonArrayDouble() {
        double[] expected = new double[]{1.1, 2.2, 3.3, 4.4};
        double[] actual = gson.fromJson(
                jsonObjectWriter.toJson(expected),
                double[].class
        );
        assertEquals(0, Arrays.compare(expected, actual));
    }

    @Test
    void toJsonArrayObject() {
        ExperimentalMouse[] expected = new ExperimentalMouse[]{
                ExperimentalMouse.createDefault(),
                ExperimentalMouse.createDefault()
        };
        String actualString = jsonObjectWriter.toJson(expected);
        ExperimentalMouse[] actual = gson.fromJson(
                actualString,
                ExperimentalMouse[].class
        );
        assertArrayEquals(expected, actual);
    }

    @Test
    void toJsonCollection() {
        List<ExperimentalMouse> expected = new ArrayList<>();
        expected.add(ExperimentalMouse.createDefault());
        expected.add(ExperimentalMouse.createDefault());
        List<ExperimentalMouse> actual = gson.fromJson(
                jsonObjectWriter.toJson(expected),
                List.class
        );
        assertEquals(expected, actual);
    }

    @Test
    void toJsonByte() {
        byte expected = (byte) 'b';
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        byte.class
                )
        );
    }

    @Test
    void toJsonInt() {
        int expected = 123456789;
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        int.class
                )
        );
    }

    @Test
    void toJsonShort() {
        short expected = (short) 987654321;
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        short.class
                )
        );
    }

    @Test
    void toJsonLong() {
        long expected = 345672345;
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        long.class
                )
        );
    }

    @Test
    void toJsonDouble() {
        double expected = 123.123;
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        double.class
                )
        );
    }

    @Test
    void toJsonObject() {
        ExperimentalMouse expected = ExperimentalMouse.createDefault();
        ExperimentalMouse actual = gson.fromJson(
                jsonObjectWriter.toJson(expected),
                ExperimentalMouse.class
        );
        assertEquals(
                expected,
                actual
        );
    }

    @Test
    void toJsonString() {
        String expected = "expected";
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        String.class
                )
        );
    }

    @Test
    void toJsonFloat() {
        gson.toJson(ExperimentalMouse.createDefault());
        float expected = 312.123F;
        assertEquals(
                expected,
                gson.fromJson(
                        jsonObjectWriter.toJson(expected),
                        float.class
                )
        );
    }

}