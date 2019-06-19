package ru.otus.homework;

public interface DB {
    void create(AtmMemento atmMemento);

    AtmMemento read(int index);
}