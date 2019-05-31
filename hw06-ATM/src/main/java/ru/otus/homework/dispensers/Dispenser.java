package ru.otus.homework.dispensers;

import ru.otus.homework.CashOutException;
import ru.otus.homework.banknotes.Banknote;
import ru.otus.homework.cassettes.EmptyCasseteException;

import java.util.List;

public interface Dispenser {
    int putIntoBuckets(List<Banknote> banknotes);

    List<Banknote> getBanknotes(int sum) throws CashOutException, EmptyCasseteException;

    int getBalance();
}
