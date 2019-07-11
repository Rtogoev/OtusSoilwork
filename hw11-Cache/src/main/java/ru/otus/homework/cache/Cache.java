package ru.otus.homework.cache;

public interface Cache<T> {

    void put(T value);

    T get(T key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
