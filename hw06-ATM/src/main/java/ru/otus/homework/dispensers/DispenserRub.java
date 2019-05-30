package ru.otus.homework.dispensers;

import ru.otus.homework.banknotes.Banknote;
import ru.otus.homework.cassettes.Cassette;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DispenserRub implements Dispenser {
    private final Set<Cassette> cassettes = new HashSet<>();

    @Override
    public int putIntoBuckets(List<Banknote> banknotes) {
        return 0;
    }

    @Override
    public List<Banknote> getBanknotes(int sum) {
        return null;
    }

    @Override
    public int getBalance() {
        return 0;
    }
}
