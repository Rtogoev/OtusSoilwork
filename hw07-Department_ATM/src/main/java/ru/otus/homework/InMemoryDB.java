package ru.otus.homework;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDB implements DB {
    private List<AtmMemento> data;

    public InMemoryDB() {
        this.data = new ArrayList<>();
    }

    @Override
    public void create(AtmMemento atmMemento) {
        data.add(atmMemento);
    }

    @Override
    public AtmMemento read(int index) {
        return data.get(index);
    }
}
