package ru.otus.homework;

public class Count {
    private volatile int value;

    public Count(int value) {
        this.value = value;
    }

    public int incrementAndGet() {
        value++;
        return value;
    }

    public int decreaseAndGet() {
        value--;
        return value;
    }

    public int getValue() {
        return value;
    }
}
