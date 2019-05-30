package ru.otus.homework.cassettes;

import ru.otus.homework.banknotes.Banknote;
import ru.otus.homework.nominals.Nominal;

public interface Cassette {
    Nominal getNominal();
    int getAmount();
    boolean addBanknote(Banknote banknote);
    Banknote getBanknote() throws EmptyCasseteException;
}
