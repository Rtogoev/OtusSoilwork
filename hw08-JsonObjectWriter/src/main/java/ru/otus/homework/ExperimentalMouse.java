package ru.otus.homework;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ExperimentalMouse {
    private byte aByte;
    private int anInt;
    private short aShort;
    private long aLong;
    private float aFloat;
    private double aDouble;
    private String string;
    private String[] array;
    private List<String> list;

    public ExperimentalMouse(
            byte aByte,
            int anInt,
            short aShort,
            long aLong,
            float aFloat,
            double aDouble,
            String string,
            String[] array,
            List<String> list
    ) {
        this.aByte = aByte;
        this.anInt = anInt;
        this.aShort = aShort;
        this.aLong = aLong;
        this.aFloat = aFloat;
        this.aDouble = aDouble;
        this.string = string;
        this.array = array;
        this.list = list;
    }

    public static ExperimentalMouse createDefault() {
        return new ExperimentalMouse(
                (byte) 'b',
                1418998765,
                Short.parseShort("2613"),
                356596L,
                8786784F,
                515234D,
                "default",
                new String[]{"default1", "default2"},
                Arrays.asList("default3", "default4")
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentalMouse that = (ExperimentalMouse) o;
        return aByte == that.aByte &&
                anInt == that.anInt &&
                aShort == that.aShort &&
                aLong == that.aLong &&
                Float.compare(that.aFloat, aFloat) == 0 &&
                Double.compare(that.aDouble, aDouble) == 0 &&
                Objects.equals(string, that.string) &&
                Arrays.equals(array, that.array) &&
                Objects.equals(list, that.list);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(aByte, anInt, aShort, aLong, aFloat, aDouble, string, list);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }
}
