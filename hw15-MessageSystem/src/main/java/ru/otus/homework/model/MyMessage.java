package ru.otus.homework.model;

public interface MyMessage<U> {
    U getValue();

    void setValue(U value);
}
