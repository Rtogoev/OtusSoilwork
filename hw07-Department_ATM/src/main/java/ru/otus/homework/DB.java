package ru.otus.homework;

public interface DB {
    void create(StartStateMemento startStateMemento);

    StartStateMemento read(int index);
}