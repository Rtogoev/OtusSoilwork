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
                (byte) 'b',
                1418998765,
                Short.parseShort("2613"),
                356596L,
                8786784F,
                515234D,
                "default"
        );
    }

    public byte getaByte() {
        return aByte;
    }

    public void setaByte(byte aByte) {
        this.aByte = aByte;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public short getaShort() {
        return aShort;
    }

    public void setaShort(short aShort) {
        this.aShort = aShort;
    }

    public long getaLong() {
        return aLong;
    }

    public void setaLong(long aLong) {
        this.aLong = aLong;
    }

    public float getaFloat() {
        return aFloat;
    }

    public void setaFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public double getaDouble() {
        return aDouble;
    }

    public void setaDouble(double aDouble) {
        this.aDouble = aDouble;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
