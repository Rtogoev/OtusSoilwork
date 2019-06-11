package ru.otus.homework;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDB implements DB {
    private List<StartStateMemento> data;

    public InMemoryDB() {
        this.data = new ArrayList<>();
    }

    @Override
    public void create(StartStateMemento startStateMemento) {
        data.add(startStateMemento);
    }

    @Override
    public StartStateMemento read(int index) {
        return data.get(index);
    }
}
