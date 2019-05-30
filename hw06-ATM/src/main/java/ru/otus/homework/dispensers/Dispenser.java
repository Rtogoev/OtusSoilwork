package ru.otus.homework.dispensers;

import ru.otus.homework.banknotes.Banknote;

import java.util.List;

public interface Dispenser {
    int putIntoBuckets(List<Banknote> banknotes);

    List<Banknote> getBanknotes(int sum);

    int getBalance();
}
