package ru.otus.homework;

public class ExperimentalMouse {
    private byte aByte;
    private int anInt;
    private short aShort;
    private long aLong;
    private float aFloat;
    private double aDouble;
    private String string;

    public ExperimentalMouse(
            byte aByte,
            int anInt,
            short aShort,
            long aLong,
            float aFloat,
            double aDouble,
            String string
    ) {
        this.aByte = aByte;
        this.anInt = anInt;
        this.aShort = aShort;
        this.aLong = aLong;
        this.aFloat = aFloat;
        this.aDouble = aDouble;
        this.string = string;
    }

    public static ExperimentalMouse createDefault() {
        return new ExperimentalMouse(
                Byte.parseByte("0"),
                1,
                Short.parseShort("2"),
                3L,
                4F,
                5D,
                "String"
        );
    }
}
