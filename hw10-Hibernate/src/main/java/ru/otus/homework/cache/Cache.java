package ru.otus.homework.cache;

import java.util.Set;

public interface Cache<T> {

    void put(T value);

    T get(T key);

    void remove(T key);

    Set<T> getAll();
}
