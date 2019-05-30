package ru.otus.homework.cassettes;

import ru.otus.homework.banknotes.Banknote;
import ru.otus.homework.nominals.Nominal;

import java.util.LinkedList;
import java.util.List;

public abstract class CassetteGeneral implements Cassette {

    private static final List<Banknote> banknotes = new LinkedList<>();

    @Override
    public abstract Nominal getNominal();

    @Override
    public int getAmount() {
        return banknotes.size();
    }

    @Override
    public boolean addBanknote(Banknote banknote) {
        return banknotes.add(banknote);
    }

    @Override
    public Banknote getBanknote() throws EmptyCasseteException {
        if (banknotes.size() == 0) {
            throw new EmptyCasseteException();
        }
        return banknotes.get(0);
    }
}
